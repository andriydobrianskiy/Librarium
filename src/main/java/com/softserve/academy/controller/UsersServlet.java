package com.softserve.academy.controller;

import com.softserve.academy.service.UserService;
import com.softserve.academy.service.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UsersServlet.class);
    private static final UserService USER_SERVICE = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("users", USER_SERVICE.getAllDebtors());
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);

            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", USER_SERVICE.getAllUsers());
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
}
