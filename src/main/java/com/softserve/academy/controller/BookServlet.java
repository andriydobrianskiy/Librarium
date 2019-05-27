package com.softserve.academy.controller;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.service.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BookServlet", urlPatterns = {"/book/*"})
public class BookServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BookServlet.class);
    private static final BookService BOOK_SERVICE = new BookServiceImpl();
    private static final CopyService COPY_SERVICE = new CopyServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final OrdersService ORDERS_SERVICE = new OrdersServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String copy_id = request.getParameter("copy_id");
        String reader_id = request.getParameter("reader_select");
        String book_id = request.getParameter("book_id");

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            try {
                boolean success = ORDERS_SERVICE.orderCopy(copy_id, reader_id, book_id, user.getId());
                LOGGER.debug("Ordering copy is successful: " + success);
            } catch (IllegalArgumentException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = 0;
        try {
            bookId = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            Book book = BOOK_SERVICE.getBookById(bookId);
            request.setAttribute("book", book);
            request.setAttribute("averageTimeOfReading", BOOK_SERVICE.getAverageTimeOfReading(book));
            request.setAttribute("averageUserAgeByBook", BOOK_SERVICE.getUserAverageAgeByBookId(book));
            request.setAttribute("averageUserAgesForAuthors", USER_SERVICE.getUsersAverageAgesForAuthors(book.getAuthors()));
            //request.setAttribute("copies", COPY_SERVICE.getAllCopiesByBook(book));
            request.setAttribute("copies", COPY_SERVICE.getAllCopiesWithOrdersCountByBook(book));
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("users", USER_SERVICE.getAllUsers());


        request.setAttribute("user", request.getSession().getAttribute("user"));

        request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
    }
}
