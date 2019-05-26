package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface CopyDao {
    List<Copy> getAllCopiesByBookId(int bookId);

    boolean insertCopy(Copy copy);

    List<Copy> getAllCopiesByUser(User user);

    List<Copy> getAllCopiesWithOrdersCountByBookId(int bookId);

    boolean changeCopyAvailability(int copyId, boolean toAvailable);

    List<Copy> getCopyByBook (Date datefrom, Date dateto);

    List<Copy> getCopyCountisEmty(Book book);
}
