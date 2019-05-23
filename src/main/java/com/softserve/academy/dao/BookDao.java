package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooksByUser(User user);
    int getCountOfBookOrdersByBookId(int bookId);
}
