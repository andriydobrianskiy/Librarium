package com.softserve.academy.dao;

import com.softserve.academy.Entity.User;

public interface UserDao {
    int getDaysOfUsingLibraryByUser(User user);
}
