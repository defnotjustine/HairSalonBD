package com.pwr.controller;

import com.pwr.model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    public static boolean loginClient(String telephone, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM clients WHERE telephone_nr = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, telephone);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return true; // Logowanie udane
                } else {
                    return false; // Błąd logowania
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
