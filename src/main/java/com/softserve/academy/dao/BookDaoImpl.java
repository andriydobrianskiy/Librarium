package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookDaoImpl implements BookDao {
    private static final Logger LOGGER = Logger.getLogger(BookDaoImpl.class);

    @Override
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

    @Override
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

    @Override
    public Book getBookById(int bookId) {
        Book book = new Book();
        String query = "select name, description, page_quantity\n" +
            "from book\n" +
            "where id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                book.setId(bookId);
                book.setName(rs.getString("name"));
                book.setDescription(rs.getString("description"));
                book.setPageQuantity(rs.getInt("page_quantity"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return book;
    }

    @Override
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

    @Override
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
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        if (bookCount == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean insertBook(Book book) {
        String query = "INSERT INTO book(user_id, name, description, page_quantity) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, book.getCreatorId().getId());
            pst.setString(2, book.getName());
            pst.setString(3, book.getDescription());
            pst.setInt(4, book.getPageQuantity());
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

    @Override
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

    @Override
    public List<Book> getOrderedListOfBooksInPeriod(Date startDate, Date endDate, boolean sortAsc) {
        Book book;
        List<Book> orderedBooks = new ArrayList<>();

        String query = "select count(book_id) as booksQuantity, " +
            "book.id, book.name, book.description, book.page_quantity " +
            "from orders left join book on book.id = orders.book_id " +
            "where orders.take_date Between ? and ? " +
            "Group by orders.book_ID " +
            "Order by COUNT(book_id) ";
        if (sortAsc) {
            query += "ASC";
        } else {
            query += "DESC";
        }
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            pst.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                book.setOrdersQuantity(rs.getInt("booksQuantity"));
                book.setId(rs.getInt("book.id"));
                book.setName(rs.getString("book.name"));
                book.setDescription(rs.getString("book.description"));
                book.setPageQuantity(rs.getInt("book.page_quantity"));

                orderedBooks.add(book);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return orderedBooks;
    }

    @Override
    public List<Book> getBooksByAuthors(Author author) {
        Book book = new Book();
        ArrayList<Book> bookArrayList = new ArrayList<>();
        String query = "Select\n" +
            "\t\tB.id, B.Name, B.Description, B.Page_Quantity, A.firstname, A.lastname\n" +
            "from  book AS B\n" +
            "               Left join bookauthor AS BA ON BA.book_id = B.id\n" +
            "               Left JOIN author AS A ON A.id = BA.author_id\n" +
            "WHERE" +
            "  A.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, author.getId());
            //  pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setDescription(rs.getString(3));
                book.setPageQuantity(rs.getInt(4));
                bookArrayList.add(book);
            }


        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return bookArrayList;
    }

    @Override
    public int getBookByUserAverageAge(Book book) {
        int dayCount = 0;

        String query = "Select ROUND(AVG(datediff(convert(current_timestamp, date), CONVERT(birthday_date, date))), 0) AS dayCount\n" +
            "from\n" +
            "Orders\n" +
            "left join user  On user.id = reader_id\n" +
            "WHERE\n" +
            "book_id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, book.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dayCount = (rs.getInt("dayCount"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return dayCount;
    }

    @Override
    public int getCountBooksPublishingInPeriodOfIndependence(int year) {
        String query = "SELECT DISTINCT book_id FROM copy WHERE publication_year >=?";
        int count = 0;
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, year);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                count++;
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return count;
    }

}
