package pharmasync.gui;

import pharmasync.dao.MedicineDAO;
import pharmasync.model.Medicine;

import javax.swing.*;
import java.awt.*;

public class EditMedicineScreen extends JFrame {
    private JTextField medicineField;
    private JTextField companyField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField expiryField;

    public EditMedicineScreen(
            String medicineName,
            String company,
            String category,
            String quantity,
            String price,
            String expiryDate
    ) {

        setTitle("Edit Medicine");

        setSize(500, 760);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Edit Medicine");

        title.setBounds(150, 20, 250, 40);

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 26)
        );

        add(title);

        setVisible(true);
        JLabel medicineLabel = new JLabel("Medicine Name");

        medicineLabel.setBounds(40, 90, 150, 25);

        medicineLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        add(medicineLabel);

        medicineField = new JTextField();

        medicineField.setBounds(40, 120, 400, 40);

        medicineField.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        add(medicineField);
        JLabel companyLabel = new JLabel("Company");

        companyLabel.setBounds(40, 180, 150, 25);

        companyLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        add(companyLabel);

        companyField = new JTextField();

        companyField.setBounds(40, 210, 400, 40);

        companyField.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        add(companyField);
        JLabel categoryLabel = new JLabel("Category");

        categoryLabel.setBounds(40, 270, 150, 25);

        categoryLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        add(categoryLabel);

        categoryField = new JTextField();

        categoryField.setBounds(40, 300, 400, 40);

        categoryField.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        add(categoryField);
        JLabel quantityLabel = new JLabel("Quantity");

        quantityLabel.setBounds(40, 360, 150, 25);

        quantityLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        add(quantityLabel);

        quantityField = new JTextField();

        quantityField.setBounds(40, 390, 400, 40);

        quantityField.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        add(quantityField);

        JLabel priceLabel = new JLabel("Price");

        priceLabel.setBounds(40, 450, 150, 25);

        priceLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        add(priceLabel);

        priceField = new JTextField();

        priceField.setBounds(40, 480, 400, 40);

        priceField.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        add(priceField);

        JLabel expiryLabel = new JLabel("Expiry Date");

        expiryLabel.setBounds(40, 540, 150, 25);

        expiryLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        add(expiryLabel);

        expiryField = new JTextField();

        expiryField.setBounds(40, 570, 400, 40);

        expiryField.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        expiryField.setToolTipText("DD-MM-YYYY");

        add(expiryField);

        JButton updateButton = new JButton("Update");

        updateButton.setBounds(90, 630, 130, 45);

        updateButton.setBackground(
                new Color(46, 204, 113)
        );

        updateButton.setForeground(Color.WHITE);

        updateButton.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        updateButton.setFocusPainted(false);

        add(updateButton);

        updateButton.addActionListener(e -> {

            String updatedMedicineName = medicineField.getText();

            String updatedCompany = companyField.getText();

            String updatedCategory = categoryField.getText();

            int updatedQuantity =
                    Integer.parseInt(quantityField.getText());

            double updatedPrice =
                    Double.parseDouble(priceField.getText());

            String updatedExpiryDate =
                    expiryField.getText();

            Medicine medicine = new Medicine();

            medicine.setMedicineName(updatedMedicineName);
            medicine.setCompany(updatedCompany);
            medicine.setCategory(updatedCategory);
            medicine.setQuantity(updatedQuantity);
            medicine.setPrice(updatedPrice);
            medicine.setExpiryDate(updatedExpiryDate);

            MedicineDAO dao = new MedicineDAO();
            boolean updated = dao.updateMedicine(medicine);

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Medicine Updated Successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to Update Medicine!"
                );
            }
        });

        JButton cancelButton = new JButton("Cancel");

        cancelButton.setBounds(260, 630, 130, 45);

        cancelButton.setBackground(
                new Color(231, 76, 60)
        );

        cancelButton.setForeground(Color.WHITE);

        cancelButton.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        cancelButton.setFocusPainted(false);

        add(cancelButton);

        cancelButton.addActionListener(e -> {
            dispose();
        });

        medicineField.setText(medicineName);
        companyField.setText(company);
        categoryField.setText(category);
        quantityField.setText(quantity);
        priceField.setText(price);
        expiryField.setText(expiryDate);

        setVisible(true);
    }
}