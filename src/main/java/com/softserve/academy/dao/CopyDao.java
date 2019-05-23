package com.softserve.academy.dao;

import com.softserve.academy.Entity.Copy;

import java.util.List;

public interface CopyDao {
    List<Copy> getAllCopiesByBookId(int bookId);

    boolean insertCopy(Copy copy);
}
