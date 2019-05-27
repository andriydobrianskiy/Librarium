package com.softserve.academy.service;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<Integer> getUsersAverageAgesForAuthors(List<Author> authors) throws IllegalArgumentException;

    int getUserStatisticAverageAge();

    int getUserAverageTimeOfUsingLibrary();
}
