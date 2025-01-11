package com.pwr.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JPanel {
    //private JFrame frame;
    private JButton btnRegister;
    private JButton btnLogin;

    public MainView() {
        JFrame frame = new JFrame("System rezerwacji fryzjerskich");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Przyciski do logowania i rejestracji
        btnRegister = new JButton("Zarejestruj");
        btnLogin = new JButton("Zaloguj");

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20)); // Ustawienie przycisków na środku
        panel.add(btnRegister);
        panel.add(btnLogin);

        frame.add(panel);
        frame.setVisible(true);

        // Akcje przycisków
        btnRegister.addActionListener(e -> {
            new RegistrationView(); // Otwiera okno rejestracji
            frame.setVisible(false); // Ukrywa główne okno
        });

        btnLogin.addActionListener(e -> {
            new LoginView(); // Otwiera okno logowania
            frame.setVisible(false); // Ukrywa główne okno
        });
    }
}
