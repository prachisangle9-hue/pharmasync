package pharmasync.gui;

import javax.swing.*;
import java.awt.*;
import pharmasync.UserDAO;

public class LoginScreen extends JFrame {


    // ==========================
    // Components
    // ==========================
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {

        // ==========================
        // Window Properties
        // ==========================
        setTitle("PharmaSync Login");

        setSize(500, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(24, 43, 73));

        setLayout(null);

        // ==========================
        // Title
        // ==========================
        JLabel title = new JLabel("💊 PHARMASYNC");

        title.setBounds(100, 40, 300, 40);

        title.setForeground(Color.WHITE);

        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        add(title);

        // ==========================
        // Subtitle
        // ==========================
        JLabel subtitle = new JLabel("Pharmacy Management System");

        subtitle.setBounds(100, 80, 300, 25);

        subtitle.setForeground(new Color(210, 220, 240));

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        add(subtitle);

        // ==========================
        // Username Label
        // ==========================
        JLabel userLabel = new JLabel("Username");

        userLabel.setBounds(80, 150, 120, 25);

        userLabel.setForeground(Color.WHITE);

        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(userLabel);

        // ==========================
        // Username TextField
        // ==========================
        usernameField = new JTextField();

        usernameField.setBounds(80, 180, 330, 40);

        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        add(usernameField);

        // ==========================
        // Password Label
        // ==========================
        JLabel passwordLabel = new JLabel("Password");

        passwordLabel.setBounds(80, 240, 120, 25);

        passwordLabel.setForeground(Color.WHITE);

        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(passwordLabel);

        // ==========================
        // Password Field
        // ==========================
        passwordField = new JPasswordField();

        passwordField.setBounds(80, 270, 330, 40);

        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        add(passwordField);

        // ==========================
        // Login Button
        // ==========================
        loginButton = new JButton("LOGIN");

        loginButton.setBounds(150, 340, 180, 45);

        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        loginButton.setBackground(new Color(52, 152, 219));

        loginButton.setForeground(Color.WHITE);

        loginButton.setFocusPainted(false);

        add(loginButton);
        loginButton.addActionListener(e -> {

            String username = usernameField.getText();

            String password = new String(passwordField.getPassword());

            UserDAO userDAO = new UserDAO();

            String role = userDAO.loginAndGetRole(username, password);

            if (role == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password!"
                );

            } else {

                dispose();

                new DashboardScreen();

            }

        });

        // ==========================
        // Window Settings
        // ==========================
        setResizable(false);

        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new LoginScreen());

    }
}