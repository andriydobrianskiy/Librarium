package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;

import java.util.List;
import java.util.Map;

public interface CopyService {
    List<Copy> getAllCopiesByBook(Book book) throws IllegalArgumentException;

    List<Copy> getAllCopiesByUser(User user) throws IllegalArgumentException;

    boolean insertCopy(Copy copy) throws IllegalArgumentException;

    List<Copy> getAllCopiesWithOrdersCountByBook(Book book) throws IllegalArgumentException;

    boolean orderCopy(Copy copy) throws IllegalArgumentException;

    boolean returnCopy(Copy copy) throws IllegalArgumentException;
}
