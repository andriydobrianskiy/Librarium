package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            LOGGER.error(e.getMessage(), e);
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
            LOGGER.error(e.getMessage(), e);
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
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
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

    @Override
    public int getUserStatisticAverageAge() {
        User user = new User();
        int averangeAge = 0;
        Map<User, Integer> mapaverangeAge = new HashMap<>();
        String query = "Select \n" +
                "ROUND(AVG(datediff(convert(current_timestamp, date), CONVERT(birthday_date, date))), 0) AS AverangeAge  \n" +
                "from \n" +
                "\tuser " ;

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                averangeAge = rs.getInt("AverangeAge");

            }


        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return averangeAge;
    }
@Override
    public Map<User,Integer> getUserStatisticCreateAt(boolean sortAsc) {
        User user = new User();
        int usedDay = 0;
        Map<User, Integer> userMap = new HashMap<>();
        String query = " Select \n" +
                "ROUND(datediff(convert(current_timestamp, date), CONVERT(created_at, date)), 0) AS UsedDay, firstname, lastname\n" +
                "from \n" +
                "\tuser " +
                "Order by UsedDay ";
        if (sortAsc) {
            query += "ASC";
        } else {
            query += "DESC";
        }
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setFirstname(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                usedDay =(rs.getInt("UsedDay"));
                userMap.put(user, usedDay);
            }


        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return userMap;
    }

    @Override
    public int getUserAverageNumber(Date dateFrom, Date dateTo) {

        int dayCount = 0;
        String query = " Select \n" +
                "\t\tROUND(AVG(X.ID), 2) AS dayCount from(\n" +
                "     Select \n" +
                "\t\t\tCOUNT(user.id) AS ID, \n" +
                "            reader_id\n" +
                "\tfrom orders  \n" +
                "\t\t\t\tleft join user  on user.id = reader_id\n" +
                "             \n" +
                "\t WHERE\n" +
                "\t\t created_at BETWEEN (? AND ?)\n" +
                "          GROUP BY reader_id\n" +
                "\t\t\t\t\t\t\t\t\t\t\t) AS X";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDate(1, dateFrom);
            pst.setDate(2,dateTo);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dayCount = rs.getInt("dayCount");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return dayCount;
    }

    @Override
    public int getAuthorByUserAverageAge(Author author) {
        int dayCount = 0;
        String query = "\t\tSELECT ROUND(AVG(datediff(convert(current_timestamp, date), CONVERT(birthday_date, date))), 0) As dayCount\n" +
                "from \n" +
                "\tOrders \n" +
                "\t\t\t\tleft join user On user.id = orders.reader_id\n" +
                "                left join bookauthor On bookauthor.book_id = orders.Book_id\n" +
                "                left join author ON author.id = .author_id\n" +
                "                 \n" +
                " WHERE \n" +
                "      author.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, author.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                dayCount = (rs.getInt("dayCount"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return dayCount;
    }
}

