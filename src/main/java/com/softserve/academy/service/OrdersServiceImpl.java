package com.softserve.academy.service;

import com.softserve.academy.dao.OrdersDao;
import com.softserve.academy.dao.OrdersDaoImpl;
import org.apache.log4j.Logger;

public class OrdersServiceImpl implements OrdersService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private static final OrdersDao ORDERS_DAO = new OrdersDaoImpl();

    @Override
    public int getQuantityOfOrdersInAllPeriod() {
        return ORDERS_DAO.getQuantityOfOrdersInAllPeriod();
    }
}


