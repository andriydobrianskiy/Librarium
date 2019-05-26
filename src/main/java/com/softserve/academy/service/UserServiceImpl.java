package com.softserve.academy.service;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.User;
import com.softserve.academy.dao.UserDao;
import com.softserve.academy.dao.UserDaoImpl;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private static final UserDao USER_DAO = new UserDaoImpl();

    @Override
    public List<User> getAllUsers() {
        return USER_DAO.getAllUsers();
    }

    @Override
    public List<Integer> getUsersAverageAgesForAuthors(List<Author> authors) throws IllegalArgumentException {
        if (authors == null) {
            throw new IllegalArgumentException("Authors list is null");
        }
        List<Integer> averageAges = new ArrayList<>();
        for (Author author : authors) {
            averageAges.add(USER_DAO.getAuthorByUserAverageAge(author) / 365);
        }
        return averageAges;
    }
}
