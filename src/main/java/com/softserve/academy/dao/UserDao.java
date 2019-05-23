package com.softserve.academy.dao;

import com.softserve.academy.Entity.User;

import java.util.List;

public interface UserDao {
    boolean insertUser(User user);

    List<User> getAllDebtors();

    List<User> getAllUsers();

    int getDaysOfUsingLibraryByUser(User user);
}
