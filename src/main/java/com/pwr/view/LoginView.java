package com.pwr.view;

import com.pwr.controller.LoginController;
import com.pwr.model.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginView extends JPanel{
    private static JTextField tfTelephone;
    private static JPasswordField pfPassword;
    private JButton btnLogin;
    private JButton btnBack;

    public LoginView() {
        JFrame frame = new JFrame("Logowanie");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tfTelephone = new JTextField(15);
        pfPassword = new JPasswordField(15);
        btnLogin = new JButton("Zaloguj");
        btnBack = new JButton("Powrót");

        panel.setLayout(new GridLayout(5, 1, 10, 10)); // Układ z 5 wierszami
        panel.add(new JLabel("Numer telefonu:"));
        panel.add(tfTelephone);
        panel.add(new JLabel("Hasło:"));
        panel.add(pfPassword);
        panel.add(btnLogin);
        panel.add(btnBack);

        frame.add(panel);
        frame.setVisible(true);

        // Akcje przycisków
        btnLogin.addActionListener(e -> {
            String telephone = tfTelephone.getText();
            String password = new String(pfPassword.getPassword());
            if (LoginController.loginClient(telephone, password)) {
                MenuView.setIsLoggedIn(true); // Ustawiamy stan logowania na true
                JOptionPane.showMessageDialog(frame, "Zalogowano pomyślnie!");
                new MenuView(); // Otwarcie okna wyboru czynności
                frame.setVisible(false); // Ukrywa okno logowania
            } else {
                JOptionPane.showMessageDialog(frame, "Błędne dane logowania.");
            }
        });

        btnBack.addActionListener(e -> {
            new MainView(); // Powrót do okna głównego
            frame.setVisible(false); // Ukrywa okno logowania
        });
    }
    public static String getPhoneNumber() {
        return tfTelephone.getText(); // Zwraca tekst wprowadzony w polu numeru telefonu
    }

    public static String getPassword() {
        return new String(pfPassword.getPassword()); // Zwraca hasło wprowadzone w polu hasła
    }

}
