package com.softserve.academy.dao;

import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;

import java.util.List;

public interface CopyDao {
    List<Copy> getAllCopiesByBookId(int bookId);
    List<Copy> getAllCopiesByUser(User user);
}
