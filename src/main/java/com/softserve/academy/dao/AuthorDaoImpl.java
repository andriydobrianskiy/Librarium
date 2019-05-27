package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private static final Logger LOGGER = Logger.getLogger(AuthorDaoImpl.class);

    @Override
    public boolean insertAuthor(Author author) {
        String query = "INSERT INTO author(user_id, firstname, lastname) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);


            pst.setInt(1, author.getCreatorId().getId());
            pst.setString(2, author.getFirstName());
            pst.setString(3, author.getLastName());
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
    public List<Author> getAuthorsByBookId(int bookId) {
        List<Author> authorList = new ArrayList<>();
        Author author;
        String query = "select author.id, author.firstname, author.lastname\n" +
            "from author left join bookauthor on author.id = bookauthor.author_id\n" +
            "left join book on book.id = bookauthor.book_id\n" +
            "where book.id = ?";
        try (Connection con = DBConnection.getDataSource().getConnection()) {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, bookId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                author = new Author();
                author.setId(rs.getInt("author.id"));
                author.setFirstName(rs.getString("author.firstname"));
                author.setLastName(rs.getString("author.lastname"));

                authorList.add(author);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return authorList;
    }
}
