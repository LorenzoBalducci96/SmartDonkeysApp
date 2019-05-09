package com.example.lorenzo.smartdonkeysapp;

public class ConnectionHandler {
    private static Connection connection = null;

    public static synchronized Connection getConnection(){
        return connection;
    }

    public static synchronized void setConnection(Connection connection){
        ConnectionHandler.connection = connection;
    }
}