package com.softserve.academy.connectDatabase;

public interface InterfaceDataBase {
    boolean connect(String url);
    void disconnect();
    //static DataSource getConnection();
    boolean reconnect();
    boolean isDbConnected();
}
