package com.softserve.academy.dao;

import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    public boolean insertUser(User user) {
        String query = "INSERT INTO user(creatorId, firstname, lastName, userName, password, " +
            "phone, address, birthday_date, contact_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setObject(1, user.getCreatorId());
            pst.setString(2, user.getFirstname());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getUserName());
            pst.setString(5, user.getPassword());
            pst.setString(6, user.getPhone());
            pst.setString(7, user.getAddress());
            pst.setDate(8, user.getBirthday_date());
            pst.setObject(9, user.getContact_type_id());
            int i = pst.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public int getDaysOfUsingLibraryByUser(User user) {
        int daysQuantity = 0;
        String query = "select datediff(convert(NOW(), date), CONVERT(created_at, date)) as daysOfUsing  \n" +
            "from user\n" +
            "where id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, user.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                daysQuantity = rs.getInt("daysOfUsing");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return daysQuantity;
    }
}

