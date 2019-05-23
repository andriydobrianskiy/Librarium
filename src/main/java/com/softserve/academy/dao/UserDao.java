package com.softserve.academy.dao;

import com.softserve.academy.Entity.User;

public interface UserDao {
    boolean insertUser(User user);

    int getDaysOfUsingLibraryByUser(User user);
}
