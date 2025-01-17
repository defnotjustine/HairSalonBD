package com.pwr.view;

import com.pwr.controller.PrevReservationController;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

public class PrevReservationsView {
    private PrevReservationController controller;

    public PrevReservationsView() {
        controller = new PrevReservationController();
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

        if (MenuView.getIsLoggedIn()) {
            phoneNumber = LoginView.getPhoneNumber();
            password = LoginView.getPassword();
        } else {
            phoneNumber = RegistrationView.getPhoneNumber();
            password = RegistrationView.getPassword();
        }

        int clientId = controller.getClientId(phoneNumber, password);

        if (clientId != -1) {
            ArrayList<Map<String, String>> reservations = controller.getPreviousReservations(clientId);
            if (!reservations.isEmpty()) {
                for (Map<String, String> res : reservations) {
                    taReservations.append(
                            "Data: " + res.get("date") +
                                    ", Godzina: " + res.get("time") +
                                    ", Usługa: " + res.get("service") +
                                    ", Cena: " + res.get("price") + " zł" +
                                    ", Fryzjer: " + res.get("hairdresser") + "\n"
                    );
                }
            } else {
                taReservations.append("Brak poprzednich rezerwacji.\n");
            }
        } else {
            taReservations.append("Błędny numer telefonu lub hasło.\n");
        }

        btnBackToMenu.addActionListener(e -> {
            new MenuView();
            frame.setVisible(false);
        });
    }
}
