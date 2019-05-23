package com.softserve.academy.dao;

import com.softserve.academy.Entity.Orders;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdersDaoImpl implements OrdersDao {

    private static final Logger LOGGER = Logger.getLogger(CopyDaoImpl.class);

    public boolean insertOrders(Orders orders) {
        String query = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setObject(1, orders.getCreatorId());
            pst.setObject(2, orders.getReader());
            pst.setObject(3, orders.getBook());
            pst.setObject(4, orders.getCopy());
            pst.setDate(5, orders.getTakeDate());
            pst.setDate(5, orders.getReturnDate());
            pst.setDate(5, orders.getDeadlineDate());
            int i = pst.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
