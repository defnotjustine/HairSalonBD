package com.pwr.view;

import com.pwr.controller.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private static JTextField tfTelephone;
    private static JPasswordField pfPassword;

    public LoginView() {
        JFrame frame = new JFrame("Logowanie");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeUI(panel, frame);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void initializeUI(JPanel panel, JFrame frame) {
        tfTelephone = new JTextField(15);
        pfPassword = new JPasswordField(15);
        JButton btnLogin = new JButton("Zaloguj");
        JButton btnBack = new JButton("Powrót");

        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.add(new JLabel("Numer telefonu:"));
        panel.add(tfTelephone);
        panel.add(new JLabel("Hasło:"));
        panel.add(pfPassword);
        panel.add(btnLogin);
        panel.add(btnBack);

        btnLogin.addActionListener(e -> handleLogin(frame));
        btnBack.addActionListener(e -> {
            new MainView();
            frame.dispose();
        });
    }

    private void handleLogin(JFrame frame) {
        String telephone = tfTelephone.getText();
        String password = new String(pfPassword.getPassword());

        if (LoginController.validateLoginInput(telephone, password)) {
            if (LoginController.loginClient(telephone, password)) {
                MenuView.setIsLoggedIn(true);
                JOptionPane.showMessageDialog(this, "Zalogowano pomyślnie!");
                new MenuView();
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Błędne dane logowania.");
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
