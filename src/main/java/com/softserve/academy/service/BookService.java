package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.dao.BookDao;
import com.softserve.academy.dao.BookDaoImpl;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static final Logger LOGGER = Logger.getLogger(BookService.class);
    private static final BookDao bookDao = new BookDaoImpl();

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public List<Book> getAllBooksByUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        } else if (user.getId() <= 0) {
            throw new IllegalArgumentException("User ID is not valid");
        }
        return bookDao.getAllBooksByUser(user);
    }

    public Book getBookByName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Book name is null or empty");
        }
        Book book = bookDao.getBookByName(name);
        if (book.getId() == 0) {
            throw new IllegalArgumentException("Book with that name is not found");
        }
        return book;
    }

    public boolean insertBook(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        }
        if (bookDao.exists(book)) {
            return false;
        } else {
            return bookDao.insertBook(book);
        }
    }

    public int getCountOfBookOrders(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return bookDao.getCountOfBookOrdersByBookId(book.getId());
    }

    public int getAverageTimeOfReading(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return bookDao.getAverageTimeOfReadingByBookId(book.getId());
    }
}
