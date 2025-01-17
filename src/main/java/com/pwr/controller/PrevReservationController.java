package com.pwr.controller;

import com.pwr.model.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrevReservationController {

    public ArrayList<Map<String, String>> getPreviousReservations(int clientId) {
        ArrayList<Map<String, String>> reservations = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT v.visit_date, v.visit_time, s.service_name, s.price, h.first_name, h.last_name " +
                    "FROM visits v " +
                    "JOIN services s ON v.service_id = s.service_id " +
                    "JOIN hairdressers h ON v.hairdresser_id = h.hairdresser_id " +
                    "WHERE v.client_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, String> reservation = new HashMap<>();
                reservation.put("date", rs.getDate("visit_date").toString());
                reservation.put("time", rs.getTime("visit_time").toString());
                reservation.put("service", rs.getString("service_name"));
                reservation.put("price", String.valueOf(rs.getDouble("price")));
                reservation.put("hairdresser", rs.getString("first_name") + " " + rs.getString("last_name"));
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reservations;
    }

    public int getClientId(String phoneNumber, String password) {
        int clientId = -1;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT client_id FROM clients WHERE telephone_nr = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, phoneNumber);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                clientId = rs.getInt("client_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return clientId;
    }
}
