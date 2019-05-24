package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    List<Book> getAllBooksByUser(User user) throws IllegalArgumentException;

    Book getBookByName(String name) throws IllegalArgumentException;

    boolean insertBook(Book book) throws IllegalArgumentException;

    int getCountOfBookOrders(Book book) throws IllegalArgumentException;

    int getAverageTimeOfReading(Book book) throws IllegalArgumentException;
}
