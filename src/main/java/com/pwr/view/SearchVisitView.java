package com.pwr.view;

import com.pwr.model.DatabaseConnection;
import com.pwr.model.Visit;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class SearchVisitView {
    private ArrayList<String> searchResults;

    public SearchVisitView() {
        JFrame frame = new JFrame("Wyszukiwanie wizyty");
        JPanel panel = new JPanel();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblDate = new JLabel("Data (YYYY-MM-DD):");
        JTextField tfDate = new JTextField(20);

        JLabel lblHairdresser = new JLabel("Fryzjer:");
        JTextField tfHairdresser = new JTextField(20);

        JLabel lblService = new JLabel("Usługa:");
        JTextField tfService = new JTextField(20);

        JButton btnSearch = new JButton("Szukaj");

        JTextArea taAvailableSlots = new JTextArea(10, 30);
        taAvailableSlots.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taAvailableSlots);

        JLabel lblReservationNumber = new JLabel("Numer rezerwacji:");
        JTextField tfReservationNumber = new JTextField(5);
        JButton btnReserve = new JButton("Rezerwuj");

        JButton btnBackToMenu = new JButton("Powrót do menu");

        panel.setLayout(new GridLayout(9, 1));
        panel.add(lblDate);
        panel.add(tfDate);
        panel.add(lblHairdresser);
        panel.add(tfHairdresser);
        panel.add(lblService);
        panel.add(tfService);
        panel.add(btnSearch);
        panel.add(scrollPane);
        panel.add(lblReservationNumber);
        panel.add(tfReservationNumber);
        panel.add(btnReserve);
        panel.add(btnBackToMenu);

        frame.add(panel);
        frame.setVisible(true);

        searchResults = new ArrayList<>();

        btnSearch.addActionListener(e -> {
            String date = tfDate.getText().trim();
            String hairdresser = tfHairdresser.getText().trim();
            String service = tfService.getText().trim();

            // Sprawdzenie, czy wszystkie pola są wypełnione
            if (date.isEmpty() || hairdresser.isEmpty() || service.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Wypełnij wszystkie pola wyszukiwania.");
                return;
            }

            searchResults.clear();
            taAvailableSlots.setText("");

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
                    searchResults.add(String.valueOf(new Visit(visitId, visitDetails)));
                    taAvailableSlots.append(visitDetails + "\n");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnReserve.addActionListener(e -> {
            String selectedNumber = tfReservationNumber.getText().trim();

            // Sprawdzenie, czy pole numeru rezerwacji jest wypełnione
            if (selectedNumber.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Wpisz numer rezerwacji.");
                return;
            }

            int visitId;

            try {
                visitId = Integer.parseInt(selectedNumber);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Wpisz poprawny numer rezerwacji.");
                return;
            }

            String phoneNumber = null;
            String password = null;

            if (MenuView.getIsLoggedIn()) {
                phoneNumber = LoginView.getPhoneNumber();
                password = LoginView.getPassword();
            } else {
                phoneNumber = RegistrationView.getPhoneNumber();
                password = RegistrationView.getPassword();
            }

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
                        return;
                    }

                    String updateQuery = "UPDATE visits SET status = 'Scheduled', client_id = ? WHERE visit_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, clientId);
                    updateStmt.setInt(2, visitId);
                    int rowsAffected = updateStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Rezerwacja pomyślnie potwierdzona!");
                        new MenuView();
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Wystąpił problem z rezerwacją.");
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Błędny numer telefonu lub hasło.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Wystąpił błąd podczas rezerwacji.");
            }
        });


        btnBackToMenu.addActionListener(e -> {
            new MenuView();
            frame.setVisible(false);
        });
    }
}