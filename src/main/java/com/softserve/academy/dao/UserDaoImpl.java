package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.User;
import com.softserve.academy.Entity.UserType;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public List<User> getAllUsers() {
        User user;
        ArrayList<User> userArrayList = new ArrayList<>();
        String query = "select id, firstname, lastName," +
            "phone, address, birthday_date " +
            "from user";

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastName(rs.getString("lastName"));
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
        LocalDate currentDate = LocalDate.now();
        List<LocalDate> userBirthDays = new ArrayList<>();

        String query = "SELECT  birthday_date FROM user WHERE birthday_date IS NOT NULL;";

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                userBirthDays.add(rs.getDate("birthday_date").toLocalDate());
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        long totalDaysDifferent = 0;
        long counterNumberOfUsers = 0;
        for (LocalDate userDate : userBirthDays) {
            long daysBetween = DAYS.between(userDate, currentDate);
            totalDaysDifferent += daysBetween;
            counterNumberOfUsers++;
        }
        int averageAge = 0;
        if (counterNumberOfUsers != 0) {
            averageAge = (int) (totalDaysDifferent / 365 / counterNumberOfUsers);
        }
        return averageAge;
    }

    @Override
    public int getUserAverageTimeOfUsingLibrary() {
        LocalDate currentDate = LocalDate.now();
        List<LocalDate> userCreatingDays = new ArrayList<>();

        String query = " SELECT  created_at FROM user where contact_type_id = 3";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                userCreatingDays.add(rs.getDate("created_at").toLocalDate());
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        long totalDaysDifferent = 0;
        long counterNumberOfUsers = 0;
        for (LocalDate userDate : userCreatingDays) {
            long daysBetween = DAYS.between(userDate, currentDate);
            totalDaysDifferent += daysBetween;
            counterNumberOfUsers++;
        }
        int averageTime = 0;
        if (counterNumberOfUsers != 0) {
            averageTime = (int) (totalDaysDifferent / counterNumberOfUsers);
        }
        return averageTime;
    }

    @Override
    public Map<User, Integer> getUserStatisticCreateAt(boolean sortAsc) {
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
                usedDay = (rs.getInt("UsedDay"));
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
            pst.setDate(2, dateTo);
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
            "                left join author ON author.id = bookauthor.author_id\n" +
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

    @Override
    public User getUserByUsername(String username) {
        User user = new User();
        String query = "select user.id, user.firstname, user.lastname, user.password, user.phone, \n" +
            "\t\tuser.address, user.birthday_date, usertype.name\n" +
            "from user left join usertype on user.contact_type_id = usertype.id\n" +
            "where user.username = ?";

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("user.id"));
                user.setFirstname(rs.getString("user.firstname"));
                user.setLastName(rs.getString("user.lastname"));
                user.setUserName(username);
                user.setPassword(rs.getString("user.password"));
                user.setPhone(rs.getString("user.phone"));
                user.setAddress(rs.getString("user.address"));
                user.setBirthday_date(rs.getDate("user.birthday_date"));

                UserType userType = new UserType();
                userType.setName(rs.getString("usertype.name"));

                user.setContact_type_id(userType);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return user;
    }

    @Override
    public User getUserById(int userid) {
        User user = new User();
        String query = "select id,created_at, user_id, firstname, lastname, phone, address\n" +
                "from user\n" +
                "where id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, userid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user.setId(userid);
                user.setCreatedAt(rs.getDate("created_at"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return user;
    }
}

