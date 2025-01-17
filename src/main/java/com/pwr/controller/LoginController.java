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
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, telephone);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Return true if a matching record is found
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Return false in case of any SQL exception
        }
    }

    public static boolean validateLoginInput(String phone, String password) {
        if (phone.isEmpty() || !phone.matches("\\d{9,15}")) {
            return false;
        }
        if (password.isEmpty() || password.length() < 5) {
            return false;
        }
        return true;
    }
}
