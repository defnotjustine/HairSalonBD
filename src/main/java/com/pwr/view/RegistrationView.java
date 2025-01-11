package com.pwr.view;

import com.pwr.controller.RegistrationController;
import com.pwr.model.Client;

import javax.swing.*;
import java.awt.*;


public class RegistrationView extends JPanel{
    private JTextField tfFirstName, tfLastName, tfEmail, tfTelephone;
    private JPasswordField pfPassword;
    private JButton btnRegister;
    private JButton btnBack;

    public RegistrationView() {
        JFrame frame = new JFrame("Rejestracja");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tfFirstName = new JTextField(15);
        tfLastName = new JTextField(15);
        tfEmail = new JTextField(15);
        tfTelephone = new JTextField(15);
        pfPassword = new JPasswordField(15);
        btnRegister = new JButton("Zarejestruj");
        btnBack = new JButton("Powrót");

        panel.setLayout(new GridLayout(6, 1, 10, 10)); // Układ z 6 wierszami
        panel.add(new JLabel("Imię:"));
        panel.add(tfFirstName);
        panel.add(new JLabel("Nazwisko:"));
        panel.add(tfLastName);
        panel.add(new JLabel("Email:"));
        panel.add(tfEmail);
        panel.add(new JLabel("Numer telefonu:"));
        panel.add(tfTelephone);
        panel.add(new JLabel("Hasło:"));
        panel.add(pfPassword);
        panel.add(btnRegister);
        panel.add(btnBack);

        frame.add(panel);
        frame.setVisible(true);

        // Akcje przycisków
        btnRegister.addActionListener(e -> {
            Client client = new Client(
                tfFirstName.getText(),
                tfLastName.getText(),
                tfEmail.getText(),
                tfTelephone.getText(),
                new String(pfPassword.getPassword())
            );
            client.setFirstName(tfFirstName.getText());
            client.setLastName(tfLastName.getText());
            client.setEmail(tfEmail.getText());
            client.setTelephone(tfTelephone.getText());
            client.setPassword(new String(pfPassword.getPassword()));

            if (RegistrationController.registerClient(client)) {
                JOptionPane.showMessageDialog(frame, "Rejestracja zakończona sukcesem!");
                new MenuView(); // Otwarcie okna wyboru czynności
                frame.setVisible(false); // Ukrywa okno rejestracji
            } else {
                JOptionPane.showMessageDialog(frame, "Numer telefonu już istnieje.");
            }
        });

        btnBack.addActionListener(e -> {
            new MainView(); // Powrót do okna głównego
            frame.setVisible(false); // Ukrywa okno rejestracji
        });
    }
}
