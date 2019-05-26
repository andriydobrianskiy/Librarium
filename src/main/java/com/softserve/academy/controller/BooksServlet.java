package com.softserve.academy.controller;

import com.softserve.academy.connectDatabase.DBConnection;
import com.softserve.academy.service.BookService;
import com.softserve.academy.service.BookServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "BooksServlet", urlPatterns = {"/books"})
public class BooksServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BooksServlet.class);
    private static final BookService BOOK_SERVICE = new BookServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String unpopularFirst = request.getParameter("unpopularFirst");

        try {
            request.setAttribute("books", BOOK_SERVICE.getOrderedBooksInPeriod(startDate, endDate, unpopularFirst));
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("unpopularFirst", unpopularFirst);

        request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();

        request.setAttribute("endDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        request.setAttribute("books", BOOK_SERVICE.getAllBooks());

        request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
    }
}
