package com.pwr.model;
import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;

    public DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7757775", "sql7757775", "GR7FQsFRaY");
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new SQLException("Nie udało się połączyć z bazą danych");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://sql7.freesqldatabase.com:3306/sql7757775", "sql7757775", "GR7FQsFRaY");
    }

    public int getClientId(String email) {
        int clientId = -1;
        String query = "SELECT client_id FROM clients WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                clientId = rs.getInt("client_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientId;
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
