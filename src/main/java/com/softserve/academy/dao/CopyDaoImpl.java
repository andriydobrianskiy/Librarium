package com.softserve.academy.dao;

import com.softserve.academy.Entity.Book;
import com.softserve.academy.Entity.Copy;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopyDaoImpl implements CopyDao {
    private static final Logger LOGGER = Logger.getLogger(CopyDaoImpl.class);

    public List<Copy> getAllCopiesByBookId(int bookId)
    {
        Book book;
        Copy copy;
        ArrayList<Copy> copyArrayList = new ArrayList<>();
        String query = "select \n" +
            "book.name, book.description, book.page_quantity, copy.id, copy.publication_year, " +
            "copy.publishing_house, copy.available \n" +
            "from \n" +
            "  copy\n" +
            "      left join book on book.id = copy.book_id\n" +
            "where\n" +
            "  book.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                book = new Book();
                copy = new Copy();
                book.setName(rs.getString(1));
                book.setDescription(rs.getString(2));
                book.setPageQuantity(rs.getInt(3));

                copy.setBookId(book);
                copy.setId(rs.getInt(4));
                copy.setPublicationYear(rs.getInt(5));
                copy.setPublishingHouse(rs.getString(6));
                copy.setAvailable(rs.getBoolean(7));

                copyArrayList.add(copy);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return copyArrayList;
    }
}
