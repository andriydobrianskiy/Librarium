package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface UserDao {
    boolean insertUser(User user);

    List<User> getAllDebtors();

    List<User> getAllUsers();

    int getDaysOfUsingLibraryByUser(User user);

    int getUserStatisticAverageAge();

    int getUserAverageTimeOfUsingLibrary();

    Map<User, Integer> getUserStatisticCreateAt(boolean sortAsc);

    int getUserAverageNumber(Date dateFrom, Date dateTo);

    int getAuthorByUserAverageAge(Author author);
}
