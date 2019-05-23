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

    public List<Book> getAllBooksByUser(User user)
    {
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
}
