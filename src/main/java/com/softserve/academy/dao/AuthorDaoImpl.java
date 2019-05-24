package com.softserve.academy.dao;

import com.softserve.academy.Entity.Author;
import com.softserve.academy.connectDatabase.DBConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
