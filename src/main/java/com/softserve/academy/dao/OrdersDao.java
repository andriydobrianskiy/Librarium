package com.softserve.academy.dao;


import java.sql.Date;
import java.util.Map;

public interface OrdersDao {
    boolean insertOrders(int creatorId, int readerId, int book_id, int copy_id, Date deadlineDate);

    Map<Integer, Integer> getAllBooksOrdersCount();

    int getOrdersCountByBookId(int bookId);

    int getMaxOrdersCount();

    int getQuantityOfOrdersInAllPeriod();
}
