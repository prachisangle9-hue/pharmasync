package pharmasync.gui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {

    public SplashScreen() {

        setTitle("PharmaSync");

        setSize(700, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUndecorated(true);

        setResizable(false);

        getContentPane().setBackground(new Color(24, 43, 73));

        // Title
        JLabel title = new JLabel("💊 PHARMASYNC", SwingConstants.CENTER);

        title.setFont(new Font("Segoe UI", Font.BOLD, 30));

        title.setForeground(Color.WHITE);

        // Subtitle
        JLabel subtitle = new JLabel(
                "Pharmacy Management System",
                SwingConstants.CENTER
        );

        JLabel loading = new JLabel(
                "Loading...",
                SwingConstants.CENTER
        );

        loading.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        loading.setForeground(Color.WHITE);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(350, 25));
        progressBar.setMaximumSize(new Dimension(350, 25));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 18)
        );

        subtitle.setForeground(
                new Color(210, 220, 240)
        );

        // Main Panel
        JPanel panel = new JPanel();

        panel.setBackground(new Color(24, 43, 73));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        loading.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());

        panel.add(title);

        panel.add(Box.createVerticalStrut(15));

        panel.add(subtitle);

        panel.add(Box.createVerticalStrut(25));

        panel.add(loading);

        panel.add(Box.createVerticalStrut(20));

        panel.add(progressBar);

        panel.add(Box.createVerticalGlue());

        add(panel);

        setVisible(true);
        new Thread(() -> {

            for (int i = 0; i <= 100; i++) {

                progressBar.setValue(i);
                if (i <= 25) {

                    loading.setText("Loading Database...");

                } else if (i <= 50) {

                    loading.setText("Loading Medicines...");

                } else if (i <= 75) {

                    loading.setText("Loading Users...");

                } else if (i <= 99) {

                    loading.setText("Loading Dashboard...");

                } else {

                    loading.setText("Opening Login...");
                }

                try {

                    Thread.sleep(30);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        }).start();
    }

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch (Exception e) {

            e.printStackTrace();

        }

        SwingUtilities.invokeLater(() -> new SplashScreen());

    }
}