package com.pwr.model;
import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    public DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hair_salon", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new SQLException("Nie udało się połączyć z bazą danych");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hair_salon", "root", "root");
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
