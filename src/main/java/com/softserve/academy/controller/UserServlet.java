package com.softserve.academy.controller;


import com.softserve.academy.Entity.User;
import com.softserve.academy.service.CopyService;
import com.softserve.academy.service.CopyServiceImpl;
import com.softserve.academy.service.UserService;
import com.softserve.academy.service.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {"/user/*"})
public class UserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserServlet.class);
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final CopyService COPY_SERVICE = new CopyServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String copy_id = request.getParameter("copy_id");
        try {
            boolean success = COPY_SERVICE.returnCopy(Integer.parseInt(copy_id));
            LOGGER.debug("Ordering copy is successful: " + success);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userid = 0;
        try {
            userid = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            User user = USER_SERVICE.getUserById(userid);
            request.setAttribute("user", user);
            request.setAttribute("daysOfUsingLibraryByUser", USER_SERVICE.getDaysOfUsingLibraryByUser(user));
              request.setAttribute("copies", COPY_SERVICE.getAllCopiesByUser(user));
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("users", USER_SERVICE.getAllUsers());

        // TODO check user(librarian) role from session
        request.setAttribute("user1", "librarian");

        request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);
    }
}
