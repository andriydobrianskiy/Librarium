package com.softserve.academy.dao;

import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;

import java.util.List;
import java.util.Map;

public interface CopyDao {
    List<Copy> getAllCopiesByBookId(int bookId);
    List<Copy> getAllCopiesByUser(User user);
    Map<Copy, Integer> getCountOfCopiesOrdersByBookId(int bookId);
}
