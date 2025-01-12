package com.pwr.view;

import com.pwr.model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            searchResults.clear();
            taAvailableSlots.setText(""); // Wyczyść pole tekstowe

            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "SELECT id, date, time, hairdresser, service FROM reservations WHERE date LIKE ? OR hairdresser LIKE ? OR service LIKE ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, "%" + date + "%");
                stmt.setString(2, "%" + hairdresser + "%");
                stmt.setString(3, "%" + service + "%");
                ResultSet rs = stmt.executeQuery();

                int counter = 1;
                while (rs.next()) {
                    String result = counter++ + ". " + rs.getString("date") + ", " + rs.getString("time") + ", " + rs.getString("hairdresser") + ", " + rs.getString("service");
                    searchResults.add(result);
                    taAvailableSlots.append(result + "\n");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnReserve.addActionListener(e -> {
            String selectedNumber = tfReservationNumber.getText().trim();
            int number;
            try {
                number = Integer.parseInt(selectedNumber);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Wpisz poprawny numer rezerwacji.");
                return;
            }

            if (number < 1 || number > searchResults.size()) {
                JOptionPane.showMessageDialog(frame, "Nieprawidłowy numer rezerwacji.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(frame, "Czy na pewno chcesz zarezerwować tę wizytę?", "Potwierdzenie rezerwacji", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(frame, "Rezerwacja potwierdzona!");
                new MenuView(); // Powrót do menu głównego
                frame.setVisible(false); // Ukrycie tego okna
            }
        });

        btnBackToMenu.addActionListener(e -> {
            new MenuView(); // Powrót do menu głównego
            frame.setVisible(false); // Ukrycie tego okna
        });
    }
}
