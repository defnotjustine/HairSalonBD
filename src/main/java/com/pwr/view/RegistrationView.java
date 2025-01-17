package com.pwr.view;

import com.pwr.controller.RegistrationController;
import com.pwr.model.Client;

import javax.swing.*;
import java.awt.*;

public class RegistrationView extends JPanel {
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfEmail;
    private static JTextField tfTelephone;
    private static JPasswordField pfPassword;

    public RegistrationView() {
        JFrame frame = new JFrame("Rejestracja");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeUI(panel, frame);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void initializeUI(JPanel panel, JFrame frame) {
        tfFirstName = new JTextField(15);
        tfLastName = new JTextField(15);
        tfEmail = new JTextField(15);
        tfTelephone = new JTextField(15);
        pfPassword = new JPasswordField(15);
        JButton btnRegister = new JButton("Zarejestruj");
        JButton btnBack = new JButton("Powrót");

        panel.setLayout(new GridLayout(6, 2, 10, 10));
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

        btnRegister.addActionListener(e -> handleRegistration(frame));
        btnBack.addActionListener(e -> {
            new MainView();
            frame.dispose();
        });
    }

    private void handleRegistration(JFrame frame) {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String email = tfEmail.getText();
        String phone = tfTelephone.getText();
        String password = new String(pfPassword.getPassword());

        if (RegistrationController.validateClientData(firstName, lastName, email, phone, password)) {
            Client client = new Client(firstName, lastName, email, phone, password);
            if (RegistrationController.registerClient(client)) {
                JOptionPane.showMessageDialog(this, "Rejestracja zakończona sukcesem!");
                new MenuView();
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Numer telefonu już istnieje.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wprowadź poprawne dane.");
        }
    }



    public static String getPhoneNumber() {
        return tfTelephone.getText();
    }

    public static String getPassword() {
        return new String(pfPassword.getPassword());
    }
}
