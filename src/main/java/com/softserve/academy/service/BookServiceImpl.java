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
    private static final BookDao bookDao = new BookDaoImpl();
    private static final AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        for (Book book : books) {
            book.setAuthors(authorDao.getAuthorsByBookId(book.getId()));
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
        return bookDao.getAllBooksByUser(user);
    }

    @Override
    public Book getBookById(int bookId) throws IllegalArgumentException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        Book book = bookDao.getBookById(bookId);
        if (book.getId() == 0) {
            throw new IllegalArgumentException("Book with that id is not found");
        }
        book.setAuthors(authorDao.getAuthorsByBookId(book.getId()));
        book.setImageUrl("photo" + book.getId());
        return book;
    }

    @Override
    public Book getBookByName(String name) throws IllegalArgumentException {
        if ((name == null) || (name.trim().isEmpty())) {
            throw new IllegalArgumentException("Book name is null or empty");
        }
        Book book = bookDao.getBookByName(name);
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
        if (bookDao.exists(book)) {
            return false;
        } else {
            return bookDao.insertBook(book);
        }
    }

    @Override
    public int getCountOfBookOrders(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return bookDao.getCountOfBookOrdersByBookId(book.getId());
    }

    @Override
    public int getAverageTimeOfReading(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return bookDao.getAverageTimeOfReadingByBookId(book.getId());
    }

    @Override
    public Map<Book, Integer> getMostPopularBooksInPeriod(Date startDate, Date endDate)
        throws IllegalArgumentException {
        if ((startDate == null) || (endDate == null)) {
            throw new IllegalArgumentException("Date is null");
        }
        return bookDao.getOrderedListOfBooksInPeriod(startDate, endDate, false);
    }

    @Override
    public Map<Book, Integer> getMostUnpopularBooksInPeriod(Date startDate, Date endDate)
        throws IllegalArgumentException {
        if ((startDate == null) || (endDate == null)) {
            throw new IllegalArgumentException("Date is null");
        }
        return bookDao.getOrderedListOfBooksInPeriod(startDate, endDate, true);
    }
}
