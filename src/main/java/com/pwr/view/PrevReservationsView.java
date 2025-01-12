package com.pwr.view;

import com.pwr.controller.LoginController;
import com.pwr.model.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrevReservationsView {
    public PrevReservationsView() {
        JFrame frame = new JFrame("Poprzednie rezerwacje");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea taReservations = new JTextArea(10, 30);
        taReservations.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taReservations);

        JButton btnBackToMenu = new JButton("Powrót do menu");

        panel.add(scrollPane);
        panel.add(btnBackToMenu);

        frame.add(panel);
        frame.setVisible(true);

        // Pobierz dane logowania (telefon, hasło) z LoginController
        String phoneNumber = LoginView.getPhoneNumber();
        String password = LoginView.getPassword();

        // Sprawdź, czy istnieje użytkownik o podanym numerze telefonu i haśle
        try (Connection conn = DatabaseConnection.getConnection()) {
            String clientQuery = "SELECT client_id FROM clients WHERE telephone_nr = ? AND password = ?";
            PreparedStatement clientStmt = conn.prepareStatement(clientQuery);
            clientStmt.setString(1, phoneNumber);
            clientStmt.setString(2, password);

            ResultSet clientRs = clientStmt.executeQuery();

            if (clientRs.next()) {
                int clientId = clientRs.getInt("client_id");

                // Pobierz rezerwacje tego klienta
                String reservationsQuery = "SELECT visit_date, service_name FROM visits " +
                        "JOIN services ON visits.service_id = services.service_id " +
                        "WHERE client_id = ?";
                PreparedStatement reservationsStmt = conn.prepareStatement(reservationsQuery);
                reservationsStmt.setInt(1, clientId);

                ResultSet reservationsRs = reservationsStmt.executeQuery();

                if (reservationsRs.next()) {
                    do {
                        taReservations.append(reservationsRs.getDate("visit_date") + ", " +
                                reservationsRs.getString("service_name") + "\n");
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
            new MenuView(); // Powrót do menu głównego
            frame.setVisible(false); // Ukrycie tego okna
        });

    }

}
