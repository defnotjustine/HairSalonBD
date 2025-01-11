package com.pwr.controller;

import com.pwr.model.Client;
import com.pwr.model.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {
    public static boolean registerClient(Client client) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM clients WHERE telephone_nr = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, client.getTelephone());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return false; // Numer telefonu już zarejestrowany
                } else {
                    String insertQuery = "INSERT INTO clients (first_name, last_name, email, telephone_nr, password) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, client.getFirstName());
                        insertStmt.setString(2, client.getLastName());
                        insertStmt.setString(3, client.getEmail());
                        insertStmt.setString(4, client.getTelephone());
                        insertStmt.setString(5, client.getPassword());
                        insertStmt.executeUpdate();
                        return true; // Rejestracja zakończona sukcesem
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
