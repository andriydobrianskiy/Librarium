package com.softserve.academy.service;

import com.softserve.academy.dao.OrdersDao;
import com.softserve.academy.dao.OrdersDaoImpl;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.Calendar;

public class OrdersServiceImpl implements OrdersService {
    private static final Logger LOGGER = Logger.getLogger(OrdersServiceImpl.class);
    private static final OrdersDao ORDERS_DAO = new OrdersDaoImpl();
    private static final CopyService COPY_SERVICE = new CopyServiceImpl();

    @Override
    public boolean orderCopy(String copyId, String readerId, String bookId, int creatorId)
        throws IllegalArgumentException {
        if ((copyId == null) || (readerId == null) || (bookId == null) || (creatorId <= 0) ||
            (copyId.isEmpty()) || (readerId.isEmpty()) || (bookId.isEmpty())) {
            throw new IllegalArgumentException("Id is not valid");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date deadlineDate = new Date(calendar.getTimeInMillis());
        // TODO check for creatorID existence and role
        // TODO check for copy, reader existence, availability
        boolean success = false;
        int copy_id;
        try {
            copy_id = Integer.parseInt(copyId);
            int reader_id = Integer.parseInt(readerId);
            int book_id = Integer.parseInt(bookId);
            if (copy_id <= 0 || reader_id <= 0 || book_id <= 0) {
                throw new IllegalArgumentException("Id is not valid");
            }
            success = ORDERS_DAO.insertOrders(creatorId, reader_id, book_id, copy_id, deadlineDate);
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("Id is not valid");
        }
        if (success) {
            return COPY_SERVICE.orderCopy(copy_id);
        }
        return false;
    }
}
