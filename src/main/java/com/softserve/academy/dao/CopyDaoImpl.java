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
}
