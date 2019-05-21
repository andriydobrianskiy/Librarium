package com.softserve.academy.connectDatabase;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.softserve.academy.Entity.User;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;

public class DBConnection implements InterfaceDataBase {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class);

    public Connection connection;

    public static final String DB_DRIVERCLASS = "jdbc:mysql://localhost/test";
    public static String serverName = getServerName();

    public static String serverChatName = getServerChatName();
    public static String databaseName = "Librarium";

    private static ComboPooledDataSource dataSource;


    public static String URL;

    public void setServerChatName(String serverName) {
        DBConnection.serverChatName = serverName;
    }

    public static String getServerChatName() {
        return serverChatName;
    }

    public void setServerName(String serverName) {
        DBConnection.serverName = serverName;
    }

    public static String getServerName() {
        return serverName;
    }

    private void createPoolConnections() {
        try {
            dataSource = new ComboPooledDataSource();

            dataSource.setUser(User.getName());
            dataSource.setDriverClass(DB_DRIVERCLASS);
            dataSource.setPassword(User.getPassword());
            dataSource.setJdbcUrl(URL);
            dataSource.setMinPoolSize(3);
            dataSource.setMaxPoolSize(500);
            dataSource.setAcquireIncrement(5);
        } catch (PropertyVetoException e) {
            LOGGER.fatal("Connection exception: ", e);
        }

    }

    @Override
    public boolean connect(String url) {
        try {
            this.URL = url;
            Class.forName(DB_DRIVERCLASS);
            connection = DriverManager.getConnection(url);
            createPoolConnections();

            return true;
        } catch (Exception e) {
            LOGGER.fatal("Connection exception: ", e);
            return false;
        }

    }
    private synchronized boolean checkConnection() {
        try {
            Class.forName(DB_DRIVERCLASS);
            connection = DriverManager.getConnection(URL);
            return true;
        } catch (Exception ex) {

            return false;
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.fatal("Exception caused by disconnect: ", e);
            DBConnection database = new DBConnection();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public boolean reconnect() {

        try {

            Timer timer = new Timer();
          //  timer.schedule(new CheckConnectionClass(), 2000, 2000);  для головного вікна

            return true;
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean isDbConnected() {
        return false;
    }


}
