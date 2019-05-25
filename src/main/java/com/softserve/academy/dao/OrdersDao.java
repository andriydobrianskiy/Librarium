package com.softserve.academy.dao;

import com.softserve.academy.Entity.Orders;

public interface OrdersDao {
    boolean insertOrders(Orders orders);
    int getQuantityOfOrdersInAllPeriod();
}
