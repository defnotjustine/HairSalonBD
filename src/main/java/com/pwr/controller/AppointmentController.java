package com.pwr.controller;

import com.pwr.model.DatabaseConnection;

import javax.swing.*;
import java.sql.*;

public class AppointmentController {
    public static void bookAppointment(int appointmentId, int clientId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE visits SET status = 'taken', client_id = ? WHERE visit_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, clientId);
                stmt.setInt(2, appointmentId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Wizyta została zarezerwowana!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas rezerwacji wizyty: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ResultSet searchAvailableAppointments() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT visit_id, visit_date FROM visits WHERE status = 'free' AND visit_date > NOW() ORDER BY visit_date ASC";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
}
