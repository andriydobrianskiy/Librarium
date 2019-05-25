package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> getAllBooks();

    List<Book> getAllBooksByUser(User user) throws IllegalArgumentException;

    Book getBookByName(String name) throws IllegalArgumentException;

    Book getBookById(int bookId) throws IllegalArgumentException;

    boolean insertBook(Book book) throws IllegalArgumentException;

    int getCountOfBookOrders(Book book) throws IllegalArgumentException;

    int getAverageTimeOfReading(Book book) throws IllegalArgumentException;

    Map<Book, Integer> getMostPopularBooksInPeriod(Date startDate, Date endDate)
        throws IllegalArgumentException;

    Map<Book, Integer> getMostUnpopularBooksInPeriod(Date startDate, Date endDate)
        throws IllegalArgumentException;
}
