package com.pwr.controller;

import com.pwr.model.Client;
import com.pwr.model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    public static boolean registerClient(Client client) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if phone number already exists
            String checkQuery = "SELECT COUNT(*) FROM clients WHERE telephone_nr = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, client.getTelephone());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Phone number already exists
            }

            // Insert new client
            String insertQuery = "INSERT INTO clients (first_name, last_name, email, telephone_nr, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, client.getFirstName());
            insertStmt.setString(2, client.getLastName());
            insertStmt.setString(3, client.getEmail());
            insertStmt.setString(4, client.getTelephone());
            insertStmt.setString(5, client.getPassword());
            insertStmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean validateClientData(String firstName, String lastName, String email, String phone, String password) {
        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
            return false;
        }
        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
            return false;
        }
        if (email.isEmpty() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return false;
        }
        if (phone.isEmpty() || !phone.matches("\\d{9,15}")) {
            return false;
        }
        if (password.isEmpty() || password.length() < 5) {
            return false;
        }
        return true;
    }
}
