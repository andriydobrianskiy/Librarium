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

    @Override
    public int getUserStatisticAverageAge() {
        return USER_DAO.getUserStatisticAverageAge();
    }

    @Override
    public int getUserAverageTimeOfUsingLibrary() {
        return USER_DAO.getUserAverageTimeOfUsingLibrary();

    }

    @Override
    public User getRegisteredUser(String username, String password) throws IllegalArgumentException {
        if ((username == null) || (password == null) ||
            (username.isEmpty()) || (password.isEmpty())) {
            throw new IllegalArgumentException("User credentials is empty");
        }
        User user = USER_DAO.getUserByUsername(username);
        if (user.getId() == 0) {
            throw new IllegalArgumentException("User with that username is not found");
        }
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new IllegalArgumentException("Password is not valid");
        }
    }


    @Override
    public User getUserById(int userid) throws IllegalArgumentException {
        if (userid <= 0) {
            throw new IllegalArgumentException("Book ID is not valid");
        }
        User user = USER_DAO.getUserById(userid);
        if (user.getId() == 0) {
            throw new IllegalArgumentException("Book with that id is not found");
        }

        return user;
    }
    @Override
    public List<User> getAllDebtors() {
        return USER_DAO.getAllDebtors();
    }
    @Override
    public int getDaysOfUsingLibraryByUser(User user) {
        return USER_DAO.getDaysOfUsingLibraryByUser(user);
    }

}
