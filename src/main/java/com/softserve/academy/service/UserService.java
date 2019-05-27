package com.softserve.academy.service;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(int userid);

    List<User> getAllDebtors();

    List<Integer> getUsersAverageAgesForAuthors(List<Author> authors) throws IllegalArgumentException;

    int getDaysOfUsingLibraryByUser (User user);

    int getUserStatisticAverageAge();

    int getUserAverageTimeOfUsingLibrary();
}
