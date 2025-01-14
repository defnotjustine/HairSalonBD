package com.pwr.view;

import com.pwr.model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrevReservationsView {
    public PrevReservationsView() {
        JFrame frame = new JFrame("Poprzednie rezerwacje");
        JPanel panel = new JPanel();
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea taReservations = new JTextArea(15, 40);
        taReservations.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taReservations);

        JButton btnBackToMenu = new JButton("Powrót do menu");

        panel.add(scrollPane);
        panel.add(btnBackToMenu);

        frame.add(panel);
        frame.setVisible(true);

        String phoneNumber = null;
        String password = null;

        // Check if the user is logged in
        if (MenuView.getIsLoggedIn()) {
            phoneNumber = LoginView.getPhoneNumber(); // Get login details
            password = LoginView.getPassword();
        } else {
            phoneNumber = RegistrationView.getPhoneNumber(); // Get registration details
            password = RegistrationView.getPassword();
        }

        // Verify if a user with the provided phone number and password exists
        try (Connection conn = DatabaseConnection.getConnection()) {
            String clientQuery = "SELECT client_id FROM clients WHERE telephone_nr = ? AND password = ?";
            PreparedStatement clientStmt = conn.prepareStatement(clientQuery);
            clientStmt.setString(1, phoneNumber);
            clientStmt.setString(2, password);

            ResultSet clientRs = clientStmt.executeQuery();

            if (clientRs.next()) {
                int clientId = clientRs.getInt("client_id");

                // Fetch reservations for this client
                String reservationsQuery =
                        "SELECT v.visit_date, v.visit_time, s.service_name, s.price, h.first_name, h.last_name " +
                                "FROM visits v " +
                                "JOIN services s ON v.service_id = s.service_id " +
                                "JOIN hairdressers h ON v.hairdresser_id = h.hairdresser_id " +
                                "WHERE v.client_id = ?";
                PreparedStatement reservationsStmt = conn.prepareStatement(reservationsQuery);
                reservationsStmt.setInt(1, clientId);

                ResultSet reservationsRs = reservationsStmt.executeQuery();

                if (reservationsRs.next()) {
                    do {
                        taReservations.append(
                                "Data: " + reservationsRs.getDate("visit_date") +
                                        ", Godzina: " + reservationsRs.getTime("visit_time") +
                                        ", Usługa: " + reservationsRs.getString("service_name") +
                                        ", Cena: " + reservationsRs.getDouble("price") + " zł" +
                                        ", Fryzjer: " + reservationsRs.getString("first_name") + " " +
                                        reservationsRs.getString("last_name") + "\n"
                        );
                    } while (reservationsRs.next());
                } else {
                    taReservations.append("Brak poprzednich rezerwacji.\n");
                }
            } else {
                taReservations.append("Błędny numer telefonu lub hasło.\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            taReservations.append("Błąd połączenia z bazą danych.\n");
        }

        btnBackToMenu.addActionListener(e -> {
            new MenuView(); // Return to main menu
            frame.setVisible(false); // Hide this window
        });
    }
}
