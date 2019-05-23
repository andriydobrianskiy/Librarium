package com.softserve.academy;

import com.softserve.academy.Entity.User;
import com.softserve.academy.connectDatabase.DBConnection;
import com.softserve.academy.dao.*;

public class Main {
    private static DBConnection dbConnection = new DBConnection();
    private static BookDao bookDao = new BookDaoImpl();
    private static CopyDao copyDao = new CopyDaoImpl();
    private static UserDao userDao = new UserDaoImpl();
    public static void main(String args[]) {
        dbConnection.connect();
        //System.out.println(bookDao.getAllBooksByUser(new User(5, "", "")));
        //System.out.println(copyDao.getAllCopiesByUser(new User(8, "", "")));
        //System.out.println(userDao.getDaysOfUsingLibraryByUser(new User(1,"","")));
        //System.out.println(bookDao.getCountOfBookOrdersByBookId(5));
        //System.out.println(copyDao.getCountOfCopiesOrdersByBookId(3));
        //System.out.println(bookDao.getAverageTimeOfReadingByBookId(6));
        //System.out.println(copyDao.getAllCopiesByBookId(1));
        //System.out.println();
    }
}
