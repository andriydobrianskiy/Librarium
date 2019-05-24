package com.softserve.academy.dao;

import com.softserve.academy.Entity.Orders;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdersDaoImpl implements OrdersDao {

    private static final Logger LOGGER = Logger.getLogger(OrdersDaoImpl.class);

    @Override
    public boolean insertOrders(Orders orders) {
        String query = "INSERT INTO orders(user_id, reader_id, book_id, copy_id, take_date," +
            " return_date, deadline_date)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, orders.getCreatorId().getId());
            pst.setInt(2, orders.getReader().getId());
            pst.setInt(3, orders.getBook().getId());
            pst.setInt(4, orders.getCopy().getId());
            pst.setDate(5, orders.getTakeDate());
            pst.setDate(5, orders.getReturnDate());
            pst.setDate(5, orders.getDeadlineDate());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

}
