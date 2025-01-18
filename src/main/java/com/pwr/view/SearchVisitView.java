package com.pwr.view;

import com.pwr.controller.VisitController;
import com.pwr.model.Visit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SearchVisitView {
    private VisitController controller;

    public SearchVisitView() {
        controller = new VisitController();
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

        btnSearch.addActionListener(e -> {
            String date = tfDate.getText().trim();
            String hairdresser = tfHairdresser.getText().trim();
            String service = tfService.getText().trim();

            // Check if any fields are empty
            if (date.isEmpty() || hairdresser.isEmpty() || service.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Wypełnij wszystkie pola wyszukiwania.");
                return;
            }

            // Validate date format
            if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(frame, "Data musi być w formacie YYYY-MM-DD.");
                return;
            }

            // Optional: Add validation for hairdresser and service names if needed
            if (!hairdresser.matches("[a-zA-Z ]+") || !service.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(frame, "Imię fryzjera i nazwa usługi mogą zawierać tylko litery.");
                return;
            }

            ArrayList<Visit> visits = controller.searchVisits(date, hairdresser, service);
            taAvailableSlots.setText("");

            if (visits.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Brak wizyt spełniających podane kryteria.");
            } else {
                for (Visit visit : visits) {
                    taAvailableSlots.append(visit.getVisitDetails() + "\n");
                }
            }
        });



        btnReserve.addActionListener(e -> {
            String selectedNumber = tfReservationNumber.getText().trim();

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

            // Check if the visitId exists in the list of available visits
            boolean isValidVisitId = false;
            String availableText = taAvailableSlots.getText();
            String[] visitLines = availableText.split("\n");

            for (String line : visitLines) {
                if (line.startsWith(String.valueOf(visitId))) {
                    isValidVisitId = true;
                    break;
                }
            }

            if (!isValidVisitId) {
                JOptionPane.showMessageDialog(frame, "Wybierz wizytę z listy.");
                return;
            }

            String phoneNumber = MenuView.getIsLoggedIn() ? LoginView.getPhoneNumber() : RegistrationView.getPhoneNumber();
            String password = MenuView.getIsLoggedIn() ? LoginView.getPassword() : RegistrationView.getPassword();

            boolean success = controller.reserveVisit(visitId, phoneNumber, password, frame);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Rezerwacja pomyślnie potwierdzona!");
                new MenuView();
                frame.setVisible(false);
            }
        });


        btnBackToMenu.addActionListener(e -> {
            new MenuView();
            frame.setVisible(false);
        });
    }
}
