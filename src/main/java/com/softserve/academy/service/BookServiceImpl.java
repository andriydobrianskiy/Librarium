package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.dao.AuthorDao;
import com.softserve.academy.dao.AuthorDaoImpl;
import com.softserve.academy.dao.BookDao;
import com.softserve.academy.dao.BookDaoImpl;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    private static final BookDao bookDao = new BookDaoImpl();
    private static final AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        setBooksAuthorsAndImageUrl(books);
        return books;
    }

    private void setBooksAuthorsAndImageUrl(final List<Book> books) {
        if (books == null) {
            return;
        }
        for (Book book : books) {
            book.setAuthors(authorDao.getAuthorsByBookId(book.getId()));
            book.setImageUrl("photo" + book.getId());
        }
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
        book.setAuthors(authorDao.getAuthorsByBookId(book.getId()));
        book.setImageUrl("photo" + book.getId());
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


    private void setBookRating(final List<Book> orderedBooks) {
        if ((orderedBooks == null) || (orderedBooks.isEmpty())) {
            return;
        }
        int maxOrderCount = Integer.max(orderedBooks.get(0).getOrdersQuantity(),
            orderedBooks.get(orderedBooks.size() - 1).getOrdersQuantity());
        for (Book book : orderedBooks) {
            book.setRating(book.getOrdersQuantity() * 100 / maxOrderCount);
        }
    }

    @Override
    public List<Book> getOrderedBooksInPeriod(String startDate, String endDate, String unpopularFirst)
        throws IllegalArgumentException {
        if ((startDate == null) || (endDate == null) ||
            (startDate.isEmpty()) || (endDate.isEmpty())) {
            throw new IllegalArgumentException("Date is null");
        }
        boolean sortAsc = unpopularFirst == null ? false : true;

        List<Book> orderedBooks = bookDao.getOrderedListOfBooksInPeriod(Date.valueOf(startDate),
            Date.valueOf(endDate), sortAsc);

        setBookRating(orderedBooks);
        setBooksAuthorsAndImageUrl(orderedBooks);
        return orderedBooks;
    }
}
