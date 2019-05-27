package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.dao.AuthorDao;
import com.softserve.academy.dao.AuthorDaoImpl;
import com.softserve.academy.dao.BookDao;
import com.softserve.academy.dao.BookDaoImpl;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    private static final BookDao BOOK_DAO = new BookDaoImpl();
    private static final AuthorDao AUTHOR_DAO = new AuthorDaoImpl();

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = BOOK_DAO.getAllBooks();
        for (Book book : books) {
            book.setAuthors(AUTHOR_DAO.getAuthorsByBookId(book.getId()));
            book.setImageUrl("photo" + book.getId());
        }
        return books;
    }

    @Override
    public List<Book> getAllBooksByUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        } else if (user.getId() <= 0) {
            throw new IllegalArgumentException("User ID is not valid");
        }
        return BOOK_DAO.getAllBooksByUser(user);
    }

    @Override
    public Book getBookByName(String name) throws IllegalArgumentException {
        if ((name == null) || (name.trim().isEmpty())) {
            throw new IllegalArgumentException("Book name is null or empty");
        }
        Book book = BOOK_DAO.getBookByName(name);
        if (book.getId() == 0) {
            throw new IllegalArgumentException("Book with that name is not found");
        }
        return book;
    }

    @Override
    public boolean insertBook(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        }
        if (BOOK_DAO.exists(book)) {
            return false;
        } else {
            return BOOK_DAO.insertBook(book);
        }
    }

    @Override
    public int getCountOfBookOrders(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return BOOK_DAO.getCountOfBookOrdersByBookId(book.getId());
    }

    @Override
    public int getAverageTimeOfReading(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return BOOK_DAO.getAverageTimeOfReadingByBookId(book.getId());
    }

    @Override
    public Map<Book, Integer> getMostPopularBooksInPeriod(Date startDate, Date endDate)
        throws IllegalArgumentException {
        if ((startDate == null) || (endDate == null)) {
            throw new IllegalArgumentException("Date is null");
        }
        return BOOK_DAO.getOrderedListOfBooksInPeriod(startDate, endDate, false);
    }

    @Override
    public Map<Book, Integer> getMostUnpopularBooksInPeriod(Date startDate, Date endDate)
        throws IllegalArgumentException {
        if ((startDate == null) || (endDate == null)) {
            throw new IllegalArgumentException("Date is null");
        }
        return BOOK_DAO.getOrderedListOfBooksInPeriod(startDate, endDate, true);
    }

    @Override
    public int getCountBooksPublishingInPeriodOfIndependence(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("Year is not valid");
        }
        return BOOK_DAO.getCountBooksPublishingInPeriodOfIndependence(year);
    }
}
