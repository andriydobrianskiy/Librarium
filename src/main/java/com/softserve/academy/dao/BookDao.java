package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooksByUser(User user);
    List<Book> getAllBooks();
    Book getBookByName(String name);
    boolean exists(Book book);
    boolean insertBook(Book book);
    int getCountOfBookOrdersByBookId(int bookId);
    int getAverageTimeOfReadingByBookId(int bookId);
}
