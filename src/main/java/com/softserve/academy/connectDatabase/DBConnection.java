package com.softserve.academy.connectDatabase;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.softserve.academy.util.PropertiesUtil;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection implements InterfaceDataBase {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class);

    private static final Properties DB_PROPERTIES = PropertiesUtil.getProperties();

    private static final String DB_URL = DB_PROPERTIES.getProperty("jdbc.url");
    private static final String DB_USER = DB_PROPERTIES.getProperty("jdbc.username");
    private static final String DB_PASS = DB_PROPERTIES.getProperty("jdbc.password");
    private static final String DB_DRIVERCLASS = DB_PROPERTIES.getProperty("jdbc.driverClassName");

    private Connection connection;

    private static ComboPooledDataSource dataSource;


    private void createPoolConnections() {
        try {
            dataSource = new ComboPooledDataSource();

            dataSource.setUser(DB_USER);
            dataSource.setDriverClass(DB_DRIVERCLASS);
            dataSource.setPassword(DB_PASS);
            dataSource.setJdbcUrl(DB_URL);
            dataSource.setMinPoolSize(3);
            dataSource.setMaxPoolSize(500);
            dataSource.setAcquireIncrement(5);
        } catch (PropertyVetoException e) {
            LOGGER.fatal("Connection exception: ", e);
        }

    }

    @Override
    public boolean connect() {
        try {
            Class.forName(DB_DRIVERCLASS);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            createPoolConnections();

            return true;
        } catch (Exception e) {
            LOGGER.fatal("Connection exception: ", e);
            return false;
        }

    }

    /*private synchronized boolean checkConnection() {
        try {
            Class.forName(DB_DRIVERCLASS);
            connection = DriverManager.getConnection(DB_URL);
            return true;
        } catch (Exception ex) {

            return false;
        }
    }
*/
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
/*
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
*/
}
