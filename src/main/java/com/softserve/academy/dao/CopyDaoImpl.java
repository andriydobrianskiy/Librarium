package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.Copy;
import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CopyDaoImpl implements CopyDao {
    private static final Logger LOGGER = Logger.getLogger(CopyDaoImpl.class);

    @Override
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

    @Override
    public boolean insertCopy(Copy copy) {
        String query = "INSERT INTO copy(user_id, publication_year, publishing_house, available, book_id)" +
            " VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, copy.getCreatorId().getId());
            pst.setInt(2, copy.getPublicationYear());
            pst.setString(3, copy.getPublishingHouse());
            pst.setBoolean(4, copy.isAvailable());
            pst.setInt(5, copy.getBookId().getId());
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

    @Override
    public List<Copy> getAllCopiesWithOrdersCountByBookId(int bookId) {
        Copy copy;
        List<Copy> copies = new ArrayList<>();

        String query = "select count(orders.copy_id) as copyOrdersCount, copy.id, \n" +
            "copy.publication_year, copy.publishing_house, copy.available\n" +
            "from copy left join orders on copy.id = orders.copy_id\n" +
            "where copy.book_id = ?\n" +
            "group by copy.id";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                copy = new Copy();

                copy.setOrdersQuantity(rs.getInt("copyOrdersCount"));
                copy.setId(rs.getInt("copy.id"));
                copy.setPublicationYear(rs.getInt("copy.publication_year"));
                copy.setPublishingHouse(rs.getString("copy.publishing_house"));
                copy.setAvailable(rs.getBoolean("copy.available"));

                copies.add(copy);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return copies;
    }

    @Override
    public boolean changeCopyAvailability(int copyId, boolean toAvailable) {
        String query = "update copy\n" +
            "set available = ?\n" +
            "where id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, toAvailable ? 1 : 0);
            pst.setInt(2, /*copy.getId()*/copyId);

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
    public List<Copy> getCopyByBook(Date datefrom, Date dateto) {
        Book book = new Book();
        Copy copy = new Copy();
        ArrayList<Copy> copyArrayList = new ArrayList<>();
        String query = "Select \n" +
            "\t\tDISTINCT B.id, B.name, B.Description, page_quantity, C.publication_year, C.publishing_house \n" +
            "from book AS B \n" +
            "\t\tleft join copy As C on C.book_id = B.ID\n" +
            "WHERE\n" +
            "\tC.publication_year BETWEEN (convert(?, datetime) AND convert(?, date))";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDate(1, datefrom);
            pst.setDate(2, dateto);
            //  pst.executeQuery();
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                copy = new Copy();
                book = new Book();

                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setDescription(rs.getString(3));
                book.setPageQuantity(rs.getInt(4));
                copy.setBookId(book);
                copy.setPublicationYear(rs.getInt(5));
                copy.setPublishingHouse(rs.getString(6));
                copyArrayList.add(copy);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return copyArrayList;
    }

    @Override
    public List<Copy> getCopyCountisEmty(Book bookID) {

        Book book = new Book();
        Copy copy = new Copy();
        ArrayList<Copy> copyArrayList = new ArrayList<>();
        String query = "Select \n" +
            "\t\tName, available\n" +
            "from \n" +
            "\tbook \n" +
            "\t\tleft join copy  On book_id = id\n" +
            "WHERE \n" +
            "     book_id = ? ";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookID.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                copy = new Copy();
                book = new Book();

                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setDescription(rs.getString(3));
                book.setPageQuantity(rs.getInt(4));
                copy.setBookId(book);
                copy.setPublicationYear(rs.getInt(5));
                copy.setPublishingHouse(rs.getString(6));
                copyArrayList.add(copy);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return copyArrayList;
    }

}
