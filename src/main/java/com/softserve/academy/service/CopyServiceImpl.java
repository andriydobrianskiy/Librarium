package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;
import com.softserve.academy.dao.CopyDao;
import com.softserve.academy.dao.CopyDaoImpl;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class CopyServiceImpl implements CopyService {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    private static final CopyDao copyDao = new CopyDaoImpl();

    @Override
    public List<Copy> getAllCopiesByBook(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return copyDao.getAllCopiesByBookId(book.getId());
    }

    @Override
    public List<Copy> getAllCopiesByUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        } else if (user.getId() <= 0) {
            throw new IllegalArgumentException("User ID is not valid");
        }
        return copyDao.getAllCopiesByUser(user);
    }

    @Override
    public boolean insertCopy(Copy copy) throws IllegalArgumentException {
        if (copy == null) {
            throw new IllegalArgumentException("Copy is null");
        }
        return copyDao.insertCopy(copy);
    }

    @Override
    public List<Copy> getAllCopiesWithOrdersCountByBook(Book book)
        throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return copyDao.getAllCopiesWithOrdersCountByBookId(book.getId());
    }

    @Override
    public boolean orderCopy(Copy copy) throws IllegalArgumentException {
        if (copy == null) {
            throw new IllegalArgumentException("Copy is null");
        } else if (copy.getId() <= 0) {
            throw new IllegalArgumentException("Copy ID is not valid");
        }
        return copyDao.changeCopyAvailability(copy, false);
    }

    @Override
    public boolean returnCopy(Copy copy) throws IllegalArgumentException {
        if (copy == null) {
            throw new IllegalArgumentException("Copy is null");
        } else if (copy.getId() <= 0) {
            throw new IllegalArgumentException("Copy ID is not valid");
        }
        return copyDao.changeCopyAvailability(copy, true);
    }
}
