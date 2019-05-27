package com.softserve.academy.controller;

import com.softserve.academy.Entity.User;
import com.softserve.academy.service.UserService;
import com.softserve.academy.service.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    private static final UserService USER_SERVICE = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("pwd");

        try {
            User user = USER_SERVICE.getRegisteredUser(username, password);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
        }
        response.sendRedirect(request.getContextPath() + "/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
