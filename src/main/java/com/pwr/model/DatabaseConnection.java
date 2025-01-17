package com.pwr.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7757775";
    private static final String USER = "sql7757775";
    private static final String PASSWORD = "GR7FQsFRaY";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
