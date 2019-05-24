package com.softserve.academy.controller;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.connectDatabase.DBConnection;
import com.softserve.academy.service.BookService;
import com.softserve.academy.service.BookServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "BooksServlet", urlPatterns = {"/books"})
public class BooksServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BooksServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();
        List<Book> bookList = new BookServiceImpl().getAllBooks();
        Map<Book, String> books = new HashMap<>();
        for (int i = 0; i < bookList.size(); i++) {
           books.put(bookList.get(i), "photo" + (i + 1));
        }
        request.setAttribute("books", books);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/pages/books.jsp");
        rd.forward(request, response);
    }
}
