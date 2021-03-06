package com.softserve.academy.service;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;
import com.softserve.academy.dao.CopyDao;
import com.softserve.academy.dao.CopyDaoImpl;
import org.apache.log4j.Logger;

import java.util.List;

public class CopyServiceImpl implements CopyService {
    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    private static final CopyDao COPY_DAO = new CopyDaoImpl();

    @Override
    public List<Copy> getAllCopiesByBook(Book book) throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return COPY_DAO.getAllCopiesByBookId(book.getId());
    }

    @Override
    public List<Copy> getAllCopiesByUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        } else if (user.getId() <= 0) {
            throw new IllegalArgumentException("User ID is not valid");
        }
        return COPY_DAO.getAllCopiesByUser(user);
    }

    @Override
    public boolean insertCopy(Copy copy) throws IllegalArgumentException {
        if (copy == null) {
            throw new IllegalArgumentException("Copy is null");
        }
        return COPY_DAO.insertCopy(copy);
    }

    @Override
    public List<Copy> getAllCopiesWithOrdersCountByBook(Book book)
        throws IllegalArgumentException {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        } else if (book.getId() <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        return COPY_DAO.getAllCopiesWithOrdersCountByBookId(book.getId());
    }

    @Override
    public boolean orderCopy(int copyId) throws IllegalArgumentException {
        if (copyId <= 0) {
            throw new IllegalArgumentException("Copy ID is not valid");
        }
        return COPY_DAO.changeCopyAvailability(copyId, false);
    }

    @Override
    public boolean returnCopy(int copyId) throws IllegalArgumentException {
        if (copyId <= 0) {
            throw new IllegalArgumentException("Copy ID is not valid");
        }
        return COPY_DAO.changeCopyAvailability(copyId, true);
    }
}
