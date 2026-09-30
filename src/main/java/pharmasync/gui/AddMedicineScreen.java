package pharmasync.gui;

import pharmasync.dao.MedicineDAO;
import pharmasync.model.Medicine;

import javax.swing.*;
import java.awt.*;

public class AddMedicineScreen extends JFrame {
    private JTextField medicineField;
    private JTextField companyField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField expiryField;

    public AddMedicineScreen() {

        setTitle("Add Medicine");

        setSize(500, 760);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Add New Medicine");

        title.setBounds(120,20,300,40);

        title.setFont(new Font("Segoe UI", Font.BOLD, 26));

        add(title);

        // ==========================
        // Medicine Name Label
        // ==========================

        JLabel medicineLabel = new JLabel("Medicine Name");

        medicineLabel.setBounds(40, 90, 150, 25);

        medicineLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(medicineLabel);

       // ==========================
       // Medicine Name TextField
       // ==========================

        medicineField = new JTextField();

        medicineField.setBounds(40, 120, 400, 40);

        medicineField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        add(medicineField);

        // ==========================
        // Company Label
        // ==========================

        JLabel companyLabel = new JLabel("Company");

        companyLabel.setBounds(40, 180, 150, 25);

        companyLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(companyLabel);

       // ==========================
        // Company TextField
       // ==========================

        companyField = new JTextField();

        companyField.setBounds(40, 210, 400, 40);

        companyField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        add(companyField);

        // ==========================
// Category Label
// ==========================

        JLabel categoryLabel = new JLabel("Category");

        categoryLabel.setBounds(40, 270, 150, 25);

        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(categoryLabel);

// ==========================
// Category TextField
// ==========================

        categoryField = new JTextField();

        categoryField.setBounds(40, 300, 400, 40);

        categoryField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        add(categoryField);

        // ==========================
// Quantity Label
// ==========================

        JLabel quantityLabel = new JLabel("Quantity");

        quantityLabel.setBounds(40, 360, 150, 25);

        quantityLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(quantityLabel);

// ==========================
// Quantity TextField
// ==========================

        quantityField = new JTextField();

        quantityField.setBounds(40, 390, 400, 40);

        quantityField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        add(quantityField);

        // ==========================
// Price Label
// ==========================

        JLabel priceLabel = new JLabel("Price");

        priceLabel.setBounds(40, 450, 150, 25);

        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(priceLabel);

// ==========================
// Price TextField
// ==========================

        priceField = new JTextField();

        priceField.setBounds(40, 480, 400, 40);

        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        add(priceField);

        // ==========================
// Expiry Date Label
// ==========================

        JLabel expiryLabel = new JLabel("Expiry Date");

        expiryLabel.setBounds(40, 540, 150, 25);

        expiryLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        add(expiryLabel);

// ==========================
// Expiry Date TextField
// ==========================

        expiryField = new JTextField();

        expiryField.setBounds(40, 570, 400, 40);

        expiryField.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        expiryField.setToolTipText("DD-MM-YYYY");

        add(expiryField);

        // ==========================
        // Save Button
        // ==========================

        JButton saveButton = new JButton("Save");

        saveButton.setBounds(90, 630, 130, 45);

        saveButton.setBackground(new Color(46, 204, 113));

        saveButton.setForeground(Color.WHITE);

        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        saveButton.setFocusPainted(false);

        add(saveButton);

        saveButton.addActionListener(e -> {

            String medicineName = medicineField.getText();

            String company = companyField.getText();

            String category = categoryField.getText();

            int quantity = Integer.parseInt(quantityField.getText());

            double price = Double.parseDouble(priceField.getText());

            String expiryDate = expiryField.getText();

            Medicine medicine = new Medicine();

            medicine.setMedicineName(medicineName);
            medicine.setCompany(company);
            medicine.setCategory(category);
            medicine.setQuantity(quantity);
            medicine.setPrice(price);
            medicine.setExpiryDate(expiryDate);

            MedicineDAO dao = new MedicineDAO();

            boolean saved = dao.insertMedicine(medicine);

            if (saved) {

                JOptionPane.showMessageDialog(this,
                        "Medicine Saved Successfully!");
                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Failed to Save Medicine!");

            }
        });

        // ==========================
// Cancel Button
// ==========================

        JButton cancelButton = new JButton("Cancel");

        cancelButton.setBounds(260, 630, 130, 45);

        cancelButton.setBackground(new Color(231, 76, 60));

        cancelButton.setForeground(Color.WHITE);

        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        cancelButton.setFocusPainted(false);

        add(cancelButton);

        setVisible(true);
    }
}
