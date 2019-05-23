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

    public int getDaysOfUsingLibraryByUser(User user)
    {
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
