package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface BookDao {
    List<Book> getAllBooksByUser(User user);

    Map<Book, Integer> getOrderedListOfBooksInPeriod(Date startDate, Date endDate, boolean sortAsc);

    boolean insertBook(Book book);

    int getCountOfBookOrdersByBookId(int bookId);

    int getAverageTimeOfReadingByBookId(int bookId);
}
