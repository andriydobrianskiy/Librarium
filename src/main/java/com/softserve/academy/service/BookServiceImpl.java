package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.dao.*;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.*;

public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    private static final BookDao BOOK_DAO = new BookDaoImpl();
    private static final AuthorDao AUTHOR_DAO = new AuthorDaoImpl();
    private static final OrdersDao ORDERS_DAO = new OrdersDaoImpl();

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = BOOK_DAO.getAllBooks();
        setBooksAuthorsAndImageUrl(books);
        setBooksOrdersQuantity(books);
        setBookRating(books);
        return books;
    }

    private void setBooksOrdersQuantity(final List<Book> books) {
        if (books == null) {
            return;
        }
        Map<Integer, Integer> ordersCount = ORDERS_DAO.getAllBooksOrdersCount();
        for (Book book : books) {
            book.setOrdersQuantity(ordersCount.getOrDefault(book.getId(), 0));
        }
    }

    private void setBooksAuthorsAndImageUrl(final List<Book> books) {
        if (books == null) {
            return;
        }
        for (Book book : books) {
            book.setAuthors(AUTHOR_DAO.getAuthorsByBookId(book.getId()));
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
        return BOOK_DAO.getAllBooksByUser(user);
    }

    @Override
    public Book getBookById(int bookId) throws IllegalArgumentException {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        Book book = BOOK_DAO.getBookById(bookId);
        if (book.getId() == 0) {
            throw new IllegalArgumentException("Book with that id is not found");
        }
        book.setAuthors(AUTHOR_DAO.getAuthorsByBookId(book.getId()));
        book.setImageUrl("photo" + book.getId());
        book.setOrdersQuantity(ORDERS_DAO.getOrdersCountByBookId(bookId));
        book.setRating(book.getOrdersQuantity() * 100 / ORDERS_DAO.getMaxOrdersCount());
        return book;
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
        book.setAuthors(AUTHOR_DAO.getAuthorsByBookId(book.getId()));
        book.setImageUrl("photo" + book.getId());
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


    private void setBookRating(final List<Book> /*orderedBooks*/books) {
        if ((/*orderedBooks*/books == null) || (/*orderedBooks*/books.isEmpty())) {
            return;
        }

        int maxOrderCount = Collections.max(books, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getOrdersQuantity() - o2.getOrdersQuantity();
            }
        }).getOrdersQuantity();

        /*int maxOrderCount = Integer.max(orderedBooks.get(0).getOrdersQuantity(),
            orderedBooks.get(orderedBooks.size() - 1).getOrdersQuantity());*/
        for (Book book : /*orderedBooks*/books) {
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

        List<Book> orderedBooks = BOOK_DAO.getOrderedListOfBooksInPeriod(Date.valueOf(startDate),
            Date.valueOf(endDate), sortAsc);

        setBookRating(orderedBooks);
        setBooksAuthorsAndImageUrl(orderedBooks);
        return orderedBooks;
    }

    @Override
    public int getUserAverageAgeByBookId(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return BOOK_DAO.getBookByUserAverageAge(book) / 365;
    }

    @Override
    public int getCountBooksPublishingInPeriodOfIndependence(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("Year is not valid");
        }
        return BOOK_DAO.getCountBooksPublishingInPeriodOfIndependence(year);
    }
}
