package com.pwr.view;

import com.pwr.controller.RegistrationController;
import com.pwr.model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class RegistrationView extends JPanel {
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfEmail;
    private static JTextField tfTelephone;
    private static JPasswordField pfPassword;
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

        frame.add(panel);
        frame.setVisible(true);

        btnRegister.addActionListener(e -> {
            if (validateInput()) {
                Client client = new Client(
                        tfFirstName.getText(),
                        tfLastName.getText(),
                        tfEmail.getText(),
                        tfTelephone.getText(),
                        new String(pfPassword.getPassword())
                );

                if (RegistrationController.registerClient(client)) {
                    JOptionPane.showMessageDialog(frame, "Rejestracja zakończona sukcesem!");
                    new MenuView();
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(frame, "Numer telefonu już istnieje.");
                }
            }
        });

        btnBack.addActionListener(e -> {
            new MainView();
            frame.setVisible(false);
        });
    }

    private boolean validateInput() {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String email = tfEmail.getText();
        String phone = tfTelephone.getText();
        String password = new String(pfPassword.getPassword());

        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Imię musi zawierać tylko litery.");
            return false;
        }

        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Nazwisko musi zawierać tylko litery.");
            return false;
        }

        if (email.isEmpty() || !Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email)) {
            JOptionPane.showMessageDialog(this, "Wprowadź prawidłowy adres email.");
            return false;
        }

        if (phone.isEmpty() || !phone.matches("\\d{9,15}")) {
            JOptionPane.showMessageDialog(this, "Numer telefonu musi zawierać od 9 do 15 cyfr.");
            return false;
        }

        if (password.isEmpty() || password.length() < 5) {
            JOptionPane.showMessageDialog(this, "Hasło musi mieć co najmniej 5 znaków.");
            return false;
        }

        return true;
    }

    public static String getPhoneNumber() {
        return tfTelephone.getText();
    }

    public static String getPassword() {
        return new String(pfPassword.getPassword());
    }
}
