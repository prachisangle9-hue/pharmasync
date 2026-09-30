package pharmasync.gui;

import javax.swing.*;
import java.awt.*;

public class MedicineScreen extends JFrame {

    public MedicineScreen() {

        setTitle("Medicines");

        setSize(1000, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        getContentPane().setBackground(new Color(245,247,250));

        JLabel title = new JLabel("💊 Medicine Management");

        title.setBounds(30,20,400,40);

        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        add(title);

        setVisible(true);
    }
}
