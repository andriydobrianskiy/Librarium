package com.softserve.academy.dao;

import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<User> getAllUsers() {
        User user;
        ArrayList<User> userArrayList = new ArrayList<>();
        String query = "select id, firstname, lastName," +
            "phone, address, birthday_date" +
            "from user";

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setBirthday_date(rs.getDate("birthday_date"));

                userArrayList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userArrayList;
    }

    @Override
    public List<User> getAllDebtors() {
        User debtor;
        ArrayList<User> debtorArrayList = new ArrayList<>();
        String query = "select user.id, user.firstname, user.lastname, \n" +
            "user.phone, user.address, user.birthday_date\n" +
            "from orders left join user on user.id = orders.reader_id\n" +
            "where current_timestamp > orders.deadline_date and orders.return_date is null";

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                debtor = new User();
                debtor.setId(rs.getInt("user.id"));
                debtor.setFirstname(rs.getString("user.firstname"));
                debtor.setLastName(rs.getString("user.lastname"));
                debtor.setPhone(rs.getString("user.phone"));
                debtor.setAddress(rs.getString("user.address"));
                debtor.setBirthday_date(rs.getDate("user.birthday_date"));

                debtorArrayList.add(debtor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return debtorArrayList;
    }

    @Override
    public boolean insertUser(User user) {
        String query = "INSERT INTO user(user_id, firstname, lastname," +
            "phone, address, birthday_date, contact_type_id) VALUES (?, ?, ?, ?, ?, ?, 3)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, user.getCreatorId().getId());
            pst.setString(2, user.getFirstname());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getPhone());
            pst.setString(5, user.getAddress());
            pst.setDate(6, user.getBirthday_date());
            int i = pst.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
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

