package com.pwr.view;

import javax.swing.*;
import java.awt.*;

public class MenuView {
    private JButton btnViewReservations;
    private JButton btnSearchVisit;

    public MenuView() {
        JFrame frame = new JFrame("Wybór czynności");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnViewReservations = new JButton("Przegląd poprzednich rezerwacji");
        btnSearchVisit = new JButton("Wyszukiwanie wizyty");

        panel.setLayout(new GridLayout(2, 1, 10, 10)); // Układ z 2 wierszami
        panel.add(btnViewReservations);
        panel.add(btnSearchVisit);

        frame.add(panel);
        frame.setVisible(true);

        // Akcje przycisków
        btnViewReservations.addActionListener(e -> {
            new PrevReservationsView(); // Otwarcie okna przeglądu rezerwacji
            frame.setVisible(false); // Ukrycie głównego menu
        });

        btnSearchVisit.addActionListener(e -> {
            new SearchVisitView(); // Otwarcie okna wyszukiwania wizyty
            frame.setVisible(false); // Ukrycie głównego menu
        });
    }
}
