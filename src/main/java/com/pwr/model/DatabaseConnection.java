package com.pwr.model;
import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    public DatabaseConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hair_salon", "root", "haslo");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Nie udało się połączyć z bazą danych");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hair_salon", "root", "haslo");
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
