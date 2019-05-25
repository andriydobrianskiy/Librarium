package com.softserve.academy.dao;

import com.softserve.academy.Entity.Orders;

import java.util.Map;

public interface OrdersDao {
    boolean insertOrders(Orders orders);

    Map<Integer, Integer> getAllBooksOrdersCount();

    int getOrdersCountByBookId(int bookId);

    int getMaxOrdersCount();
}
