package com.softserve.academy.dao;

import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class OrdersDaoImpl implements OrdersDao {

    private static final Logger LOGGER = Logger.getLogger(OrdersDaoImpl.class);

    @Override
    public boolean insertOrders(int creatorId, int readerId, int bookId, int copyId, Date deadlineDate) {
        String query = "INSERT INTO orders(user_id, reader_id, book_id, copy_id," +
            " deadline_date)" +
            " VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, /*orders.getCreatorId().getId()*/creatorId);
            pst.setInt(2, /*orders.getReader().getId()*/readerId);
            pst.setInt(3, /*orders.getBook().getId()*/bookId);
            pst.setInt(4, /*orders.getCopy().getId()*/copyId);
            pst.setDate(5, /*orders.getDeadlineDate()*/deadlineDate);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    public Map<Integer, Integer> getAllBooksOrdersCount() {
        Map<Integer, Integer> ordersCount = new HashMap<>();

        String query = "select book_id, count(book_id) as ordersQuantity\n" +
            "from orders \n" +
            "group by book_id";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ordersCount.put(rs.getInt("book_id"), rs.getInt("ordersQuantity"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return ordersCount;
    }

    public int getOrdersCountByBookId(int bookId) {
        int ordersCount = 0;

        String query = "select count(book_id) as ordersQuantity\n" +
            "from orders \n" +
            "where book_id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                ordersCount = rs.getInt("ordersQuantity");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return ordersCount;
    }

    public int getMaxOrdersCount() {
        int maximum = 0;
        String query = "select max(ordersQuantity) as maximum\n" +
            "from \n" +
            "(select count(book_id) as ordersQuantity\n" +
            "from orders\n" +
            "group by book_id) as ordersQuantities";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                maximum = rs.getInt("maximum");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return maximum;
    }
}
