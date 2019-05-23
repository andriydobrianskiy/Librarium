package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDaoImpl implements BookDao {
    private static final Logger LOGGER = Logger.getLogger(BookDaoImpl.class);

    public List<Book> getAllBooks() {
        Book book;
        ArrayList<Book> bookArrayList = new ArrayList<>();
        String query = "select id, name, description, page_quantity from book";

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setDescription(rs.getString("description"));
                book.setPageQuantity(rs.getInt("page_quantity"));

                bookArrayList.add(book);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return bookArrayList;
    }

    public List<Book> getAllBooksByUser(User user) {
        Book book;
        ArrayList<Book> bookArrayList = new ArrayList<>();
        String query = "select distinct book.id, book.name, book.description, book.page_quantity\n" +
            "from orders left join user on user.id = orders.reader_id\n" +
            "            left join book on book.id = orders.book_id\n" +
            "            WHERE user.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, user.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("book.id"));
                book.setName(rs.getString("book.name"));
                book.setDescription(rs.getString("book.description"));
                book.setPageQuantity(rs.getInt("book.page_quantity"));

                bookArrayList.add(book);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return bookArrayList;
    }

    public Book getBookByName(String name) {
        Book book = new Book();
        String query = "select id, description, page_quantity\n" +
            "from book\n" +
            "where name = ?";

        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                book.setId(rs.getInt("id"));
                book.setName(name);
                book.setDescription(rs.getString("description"));
                book.setPageQuantity(rs.getInt("page_quantity"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return book;
    }

    public boolean exists(Book book) {
        int bookCount = 0;
        String query = "select count(*) as bookCount\n" +
            "from book\n" +
            "where name = ? and description = ? and page_quantity = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, book.getName());
            pst.setString(2, book.getDescription());
            pst.setInt(3, book.getPageQuantity());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                bookCount = rs.getInt("bookCount");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (bookCount == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertBook(Book book) {
        String query = "INSERT INTO book(user_id, name, description, page_quantity) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, book.getCreatorId().getId());
            pst.setString(2, book.getName());
            pst.setString(3, book.getDescription());
            pst.setInt(4, book.getPageQuantity());
            int i = pst.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public int getCountOfBookOrdersByBookId(int bookId) {
        int bookOrdersCount = 0;
        String query = "select count(book_id) as ordersQuantity\n" +
            "from orders\n" +
            "where orders.book_id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                bookOrdersCount = rs.getInt("ordersQuantity");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return bookOrdersCount;
    }


    public int getAverageTimeOfReadingByBookId(int bookId) {
        int daysCount = 0;
        String query = "select round(avg(datediff(convert(orders.return_date, date), CONVERT(orders.take_date, date))), 0) as daysCount\n" +
            "from orders left join book on book.id = orders.book_id\n" +
            "where book.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                daysCount = rs.getInt("daysCount");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return daysCount;
    }

    public Map<Book, Integer> getOrderedListOfBooksInPeriod(Date startDate, Date endDate, boolean sortAsc) {
        Book book;
        int bookCount;
        Map<Book, Integer> countBooksInPeriod = new HashMap<>();

        String query = "select count(book_id) as booksQuantity, " +
            "book.id, book.name, book.description, book.page_quantity" +
            "from orders left join book on book.id = orders.book_id" +
            "where orders.take_date Between (? and ?)" +
            "Group by orders.book_ID" +
            "Order by COUNT(book_id) ";
        if (sortAsc) {
            query += "ASC";
        } else {
            query += "DESC";
        }
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDate(1, startDate);
            pst.setDate(2, endDate);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                bookCount = rs.getInt("booksQuantity");
                book.setId(rs.getInt("book.id"));
                book.setName(rs.getString("book.name"));
                book.setDescription(rs.getString("book.description"));
                book.setPageQuantity(rs.getInt("book.page_quantity"));
                countBooksInPeriod.put(book, bookCount);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return countBooksInPeriod;
    }
}
