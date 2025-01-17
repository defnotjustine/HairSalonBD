package com.pwr.controller;

import com.pwr.model.DatabaseConnection;
import com.pwr.model.Visit;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class VisitController {
    public ArrayList<Visit> searchVisits(String date, String hairdresser, String service) {
        ArrayList<Visit> visits = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT v.visit_id, v.visit_date, v.visit_time, h.first_name AS hairdresser, s.service_name " +
                    "FROM visits v " +
                    "JOIN hairdressers h ON v.hairdresser_id = h.hairdresser_id " +
                    "JOIN services s ON v.service_id = s.service_id " +
                    "WHERE v.status = 'Free' AND (" +
                    "v.visit_date = ? OR " +
                    "h.first_name LIKE ? OR " +
                    "s.service_name LIKE ?);";


            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, "%" + hairdresser + "%");
            stmt.setString(3, "%" + service + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int visitId = rs.getInt("visit_id");
                String visitDetails = visitId + ". " + rs.getDate("visit_date") + ", " + rs.getString("visit_time") + ", " +
                        rs.getString("hairdresser") + ", " + rs.getString("service_name");
                visits.add(new Visit(visitId, visitDetails));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return visits;
    }

    public boolean reserveVisit(int visitId, String phoneNumber, String password, JFrame frame) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String clientQuery = "SELECT client_id FROM clients WHERE telephone_nr = ? AND password = ?";
            PreparedStatement clientStmt = conn.prepareStatement(clientQuery);
            clientStmt.setString(1, phoneNumber);
            clientStmt.setString(2, password);

            ResultSet clientRs = clientStmt.executeQuery();
            if (clientRs.next()) {
                int clientId = clientRs.getInt("client_id");

                String checkQuery = "SELECT visit_id FROM visits WHERE visit_id = ? AND status = 'Free'";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, visitId);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(frame, "Wizyta o podanym numerze nie istnieje lub nie jest dostępna do rezerwacji.");
                    return false;
                }

                String updateQuery = "UPDATE visits SET status = 'Scheduled', client_id = ? WHERE visit_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, clientId);
                updateStmt.setInt(2, visitId);
                int rowsAffected = updateStmt.executeUpdate();

                return rowsAffected > 0;
            } else {
                JOptionPane.showMessageDialog(frame, "Błędny numer telefonu lub hasło.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Wystąpił błąd podczas rezerwacji.");
            return false;
        }
    }
}
