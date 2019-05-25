package com.softserve.academy.service;

import com.softserve.academy.dao.UserDao;
import com.softserve.academy.dao.UserDaoImpl;
import org.apache.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private static final UserDao USER_DAO = new UserDaoImpl();

    @Override
    public int getUserStatisticAverageAge() {
        return USER_DAO.getUserStatisticAverageAge();
    }
}
