package com.softserve.academy;

import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import com.softserve.academy.dao.BookDao;
import com.softserve.academy.dao.BookDaoImpl;

public class Main {
    private static DBConnection dbConnection = new DBConnection();
    private static BookDao bookDao = new BookDaoImpl();
    public static void main(String args[]) {
        dbConnection.connect();
        System.out.println(bookDao.getAllBooksByUser(new User(6, "", "")));
        System.out.println();


    }
}
