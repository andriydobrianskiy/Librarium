package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;

import java.sql.Date;
import java.util.List;

public interface BookDao {
    List<Book> getAllBooksByUser(User user);

    List<Book> getAllBooks();

    Book getBookByName(String name);

    Book getBookById(int bookId);

    boolean exists(Book book);

    List<Book> getOrderedListOfBooksInPeriod(Date startDate, Date endDate, boolean sortAsc);

    boolean insertBook(Book book);

    int getCountOfBookOrdersByBookId(int bookId);

    int getAverageTimeOfReadingByBookId(int bookId);

    List<Book> getBooksByAuthors(Author author);

    int getBookByUserAverageAge(Book book);
}
