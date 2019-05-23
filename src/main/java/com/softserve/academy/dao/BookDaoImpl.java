package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private static final Logger LOGGER = Logger.getLogger(BookDaoImpl.class);

    public List<Book> getAllBooksByUser(User user) {
        Book book;
        ArrayList<Book> bookArrayList = new ArrayList<>();
        String query = "Select\n" +
            "\t\tB.id, B.name, B.description, B.page_Quantity  \n" +
            "from Orders AS O\n" +
            "\t\t\tleft join user AS U ON U.id = O.reader_id\n" +
            "            left join book AS B ON B.id = O.book_id\n" +
            "WHERE U.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, user.getId());
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

    public boolean insertBook(Book book) {
        String query = "INSERT INTO book VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setObject(1, book.getCreatorId());
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
}
