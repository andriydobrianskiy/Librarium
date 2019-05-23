package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyDaoImpl implements CopyDao {
    private static final Logger LOGGER = Logger.getLogger(CopyDaoImpl.class);

    public List<Copy> getAllCopiesByBookId(int bookId) {
        Book book;
        Copy copy;
        ArrayList<Copy> copyArrayList = new ArrayList<>();
        String query = "select book.name, book.description, book.page_quantity, copy.id, \n" +
            "\tcopy.publication_year, copy.publishing_house, copy.available \n" +
            "from copy left join book on book.id = copy.book_id\n" +
            "where book.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                copy = new Copy();

                book.setId(bookId);
                book.setName(rs.getString("book.name"));
                book.setDescription(rs.getString("book.description"));
                book.setPageQuantity(rs.getInt("book.page_quantity"));

                copy.setBookId(book);
                copy.setId(rs.getInt("copy.id"));
                copy.setPublicationYear(rs.getInt("copy.publication_year"));
                copy.setPublishingHouse(rs.getString("copy.publishing_house"));
                copy.setAvailable(rs.getBoolean("copy.available"));

                copyArrayList.add(copy);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return copyArrayList;
    }

    public boolean insertCopy(Copy copy) {
        String query = "INSERT INTO copy(creatorId, publicationYear, publishingHouse, available, bookId)" +
            " VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, copy.getCreatorId());
            pst.setInt(2, copy.getPublicationYear());
            pst.setString(3, copy.getPublishingHouse());
            pst.setBoolean(4, copy.isAvailable());
            pst.setObject(5, copy.getBookId());
            int i = pst.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Copy> getAllCopiesByUser(User user) {
        Book book;
        Copy copy;
        ArrayList<Copy> copyArrayList = new ArrayList<>();
        String query = "select book.id, book.name, book.description, book.page_quantity, copy.id,\n" +
            "            copy.publication_year, copy.publishing_house, copy.available\n" +
            "from orders left join user on user.id = orders.reader_id\n" +
            "\t\t\tleft join copy on orders.copy_id = copy.id\n" +
            "            left join book on book.id = orders.book_id\n" +
            "where user.id = ?;";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, user.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                copy = new Copy();

                book.setId(rs.getInt("book.id"));
                book.setName(rs.getString("book.name"));
                book.setDescription(rs.getString("book.description"));
                book.setPageQuantity(rs.getInt("book.page_quantity"));

                copy.setBookId(book);
                copy.setId(rs.getInt("copy.id"));
                copy.setPublicationYear(rs.getInt("copy.publication_year"));
                copy.setPublishingHouse(rs.getString("copy.publishing_house"));
                copy.setAvailable(rs.getBoolean("copy.available"));

                copyArrayList.add(copy);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return copyArrayList;
    }


    public Map<Copy, Integer> getCountOfCopiesOrdersByBookId(int bookId) {
        Copy copy;
        int copyOrdersCount;
        Map<Copy, Integer> countOrdersForCopies = new HashMap<>();
        String query = "select count(orders.copy_id) as copyOrdersCount, copy.id, \n" +
            "\tcopy.publication_year, copy.publishing_house, copy.available\n" +
            "from orders left join copy on copy.id = orders.copy_id\n" +
            "where orders.book_id = ?\n" +
            "group by orders.copy_id";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                copy = new Copy();

                copyOrdersCount = rs.getInt("copyOrdersCount");
                copy.setId(rs.getInt("copy.id"));
                copy.setPublicationYear(rs.getInt("copy.publication_year"));
                copy.setPublishingHouse(rs.getString("copy.publishing_house"));
                copy.setAvailable(rs.getBoolean("copy.available"));

                countOrdersForCopies.put(copy, copyOrdersCount);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return countOrdersForCopies;
    }
}
