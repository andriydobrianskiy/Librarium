package com.softserve.academy.controller;

import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import com.softserve.academy.service.BookService;
import com.softserve.academy.service.BookServiceImpl;
import com.softserve.academy.service.UserService;
import com.softserve.academy.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class MainController extends HttpServlet {
    private static final BookService BOOK_SERVICE = new BookServiceImpl() {
    };
    private static final UserService USER_SERVICE = new UserServiceImpl() {
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();

        request.setAttribute("BooksQuantityInIndependencePeriod",
            BOOK_SERVICE.getCountBooksPublishingInPeriodOfIndependence(1991));
        request.setAttribute("AverageReaderAge", USER_SERVICE.getUserStatisticAverageAge());
//        request.setAttribute("AverageTimeOfUsingLibrary", USER_SERVICE.getUserAverageTimeOfUsingLibrary());
        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
    }
}
