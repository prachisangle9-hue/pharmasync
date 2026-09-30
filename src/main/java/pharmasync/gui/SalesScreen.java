package pharmasync.gui;

import pharmasync.dao.MedicineDAO;
import pharmasync.dao.SaleDAO;
import pharmasync.model.Medicine;
import pharmasync.model.Sale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SalesScreen extends JFrame {

    private JComboBox<String> medicineComboBox;

    private JTextField stockField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField totalField;

    private JButton sellButton;
    private JButton clearButton;
    private JButton closeButton;

    private MedicineDAO medicineDAO;
    private SaleDAO saleDAO;

    public SalesScreen() {

        medicineDAO = new MedicineDAO();
        saleDAO = new SaleDAO();

        setTitle("PharmaSync - Sales & Billing");

        setSize(650, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(null);

        getContentPane().setBackground(
                Color.WHITE
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel titleLabel =
                new JLabel("Sales & Billing");

        titleLabel.setBounds(
                190,
                25,
                300,
                45
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setForeground(
                new Color(52, 152, 219)
        );

        add(titleLabel);

        // =====================================================
        // MEDICINE
        // =====================================================

        JLabel medicineLabel =
                new JLabel("Select Medicine");

        medicineLabel.setBounds(
                70,
                100,
                160,
                30
        );

        medicineLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        add(medicineLabel);

        medicineComboBox =
                new JComboBox<>();

        medicineComboBox.setBounds(
                250,
                95,
                300,
                40
        );

        medicineComboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        add(medicineComboBox);

        // =====================================================
        // STOCK
        // =====================================================

        JLabel stockLabel =
                new JLabel("Available Stock");

        stockLabel.setBounds(
                70,
                160,
                160,
                30
        );

        stockLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        add(stockLabel);

        stockField =
                new JTextField();

        stockField.setBounds(
                250,
                155,
                300,
                40
        );

        stockField.setEditable(false);

        stockField.setBackground(
                new Color(245, 245, 245)
        );

        stockField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        add(stockField);

        // =====================================================
        // PRICE
        // =====================================================

        JLabel priceLabel =
                new JLabel("Price");

        priceLabel.setBounds(
                70,
                220,
                160,
                30
        );

        priceLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        add(priceLabel);

        priceField =
                new JTextField();

        priceField.setBounds(
                250,
                215,
                300,
                40
        );

        priceField.setEditable(false);

        priceField.setBackground(
                new Color(245, 245, 245)
        );

        priceField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        add(priceField);

        // =====================================================
        // QUANTITY
        // =====================================================

        JLabel quantityLabel =
                new JLabel("Quantity");

        quantityLabel.setBounds(
                70,
                280,
                160,
                30
        );

        quantityLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        add(quantityLabel);

        quantityField =
                new JTextField();

        quantityField.setBounds(
                250,
                275,
                300,
                40
        );

        quantityField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        add(quantityField);

        // =====================================================
        // TOTAL
        // =====================================================

        JLabel totalLabel =
                new JLabel("Total Amount");

        totalLabel.setBounds(
                70,
                340,
                160,
                30
        );

        totalLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        add(totalLabel);

        totalField =
                new JTextField();

        totalField.setBounds(
                250,
                335,
                300,
                40
        );

        totalField.setEditable(false);

        totalField.setBackground(
                new Color(235, 255, 235)
        );

        totalField.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        add(totalField);

        // =====================================================
        // COMPLETE SALE BUTTON
        // =====================================================

        sellButton =
                new JButton("Complete Sale");

        sellButton.setBounds(
                70,
                420,
                160,
                45
        );

        styleButton(
                sellButton,
                new Color(46, 204, 113)
        );

        add(sellButton);

        // =====================================================
        // CLEAR BUTTON
        // =====================================================

        clearButton =
                new JButton("Clear");

        clearButton.setBounds(
                245,
                420,
                130,
                45
        );

        styleButton(
                clearButton,
                new Color(241, 196, 15)
        );

        add(clearButton);

        // =====================================================
        // CLOSE BUTTON
        // =====================================================

        closeButton =
                new JButton("Close");

        closeButton.setBounds(
                390,
                420,
                130,
                45
        );

        styleButton(
                closeButton,
                new Color(231, 76, 60)
        );

        add(closeButton);

        // =====================================================
        // LOAD MEDICINES
        // =====================================================

        loadMedicines();

        // =====================================================
        // MEDICINE SELECTION
        // =====================================================

        medicineComboBox.addActionListener(
                e -> updateMedicineDetails()
        );

        // =====================================================
        // QUANTITY CHANGE
        // =====================================================

        quantityField.addCaretListener(
                e -> calculateTotal()
        );

        // =====================================================
        // COMPLETE SALE
        // =====================================================

        sellButton.addActionListener(
                e -> completeSale()
        );

        // =====================================================
        // CLEAR
        // =====================================================

        clearButton.addActionListener(
                e -> clearFields()
        );

        // =====================================================
        // CLOSE
        // =====================================================

        closeButton.addActionListener(
                e -> dispose()
        );

        setVisible(true);
    }

    // =========================================================
    // LOAD MEDICINES
    // =========================================================

    private void loadMedicines() {

        medicineComboBox.removeAllItems();

        ArrayList<Medicine> medicines =
                medicineDAO.getAllMedicines();

        for (
                Medicine medicine :
                medicines
        ) {

            if (
                    medicine.getQuantity() > 0
            ) {

                medicineComboBox.addItem(
                        medicine.getMedicineName()
                );
            }
        }

        if (
                medicineComboBox.getItemCount()
                        > 0
        ) {

            medicineComboBox.setSelectedIndex(0);

            updateMedicineDetails();

        } else {

            stockField.setText("");

            priceField.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "No medicines available in stock."
            );
        }
    }

    // =========================================================
    // UPDATE MEDICINE DETAILS
    // =========================================================

    private void updateMedicineDetails() {

        Object selectedItem =
                medicineComboBox.getSelectedItem();

        if (
                selectedItem == null
        ) {

            return;
        }

        String medicineName =
                selectedItem.toString();

        Medicine medicine =
                medicineDAO.getMedicineByName(
                        medicineName
                );

        if (
                medicine != null
        ) {

            stockField.setText(
                    String.valueOf(
                            medicine.getQuantity()
                    )
            );

            priceField.setText(
                    String.valueOf(
                            medicine.getPrice()
                    )
            );

            quantityField.setText("");

            totalField.setText("");
        }
    }

    // =========================================================
    // CALCULATE TOTAL
    // =========================================================

    private void calculateTotal() {

        try {

            if (
                    priceField.getText().isEmpty()
                            ||
                            quantityField.getText().isEmpty()
            ) {

                totalField.setText("");

                return;
            }

            double price =
                    Double.parseDouble(
                            priceField.getText()
                    );

            int quantity =
                    Integer.parseInt(
                            quantityField.getText()
                    );

            if (
                    quantity < 0
            ) {

                totalField.setText("");

                return;
            }

            double total =
                    price * quantity;

            totalField.setText(
                    String.format(
                            "%.2f",
                            total
                    )
            );

        } catch (Exception e) {

            totalField.setText("");
        }
    }

    // =========================================================
    // COMPLETE SALE
    // =========================================================

    private void completeSale() {

        try {

            // -------------------------------------------------
            // CHECK MEDICINE
            // -------------------------------------------------

            Object selectedItem =
                    medicineComboBox.getSelectedItem();

            if (
                    selectedItem == null
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a medicine!"
                );

                return;
            }

            String medicineName =
                    selectedItem.toString();

            // -------------------------------------------------
            // CHECK QUANTITY
            // -------------------------------------------------

            String quantityText =
                    quantityField
                            .getText()
                            .trim();

            if (
                    quantityText.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter quantity!"
                );

                return;
            }

            int quantity =
                    Integer.parseInt(
                            quantityText
                    );

            if (
                    quantity <= 0
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than 0!"
                );

                return;
            }

            // -------------------------------------------------
            // GET MEDICINE
            // -------------------------------------------------

            Medicine medicine =
                    medicineDAO.getMedicineByName(
                            medicineName
                    );

            if (
                    medicine == null
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Medicine not found!"
                );

                return;
            }

            // -------------------------------------------------
            // CHECK STOCK
            // -------------------------------------------------

            if (
                    quantity
                            > medicine.getQuantity()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient stock!\n"
                                +
                                "Available stock: "
                                +
                                medicine.getQuantity()
                );

                return;
            }

            // -------------------------------------------------
            // CALCULATE PRICE
            // -------------------------------------------------

            double price =
                    medicine.getPrice();

            double totalAmount =
                    price * quantity;

            // -------------------------------------------------
            // CREATE SALE OBJECT
            // -------------------------------------------------

            Sale sale =
                    new Sale();

            sale.setMedicineName(
                    medicineName
            );

            sale.setQuantity(
                    quantity
            );

            sale.setPrice(
                    price
            );

            sale.setTotalAmount(
                    totalAmount
            );

            // -------------------------------------------------
            // SAVE SALE
            // -------------------------------------------------

            boolean saleSaved =
                    saleDAO.insertSale(
                            sale
                    );

            if (
                    !saleSaved
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to save sale!"
                );

                return;
            }

            // -------------------------------------------------
            // REDUCE STOCK
            // -------------------------------------------------

            boolean stockReduced =
                    medicineDAO.reduceStock(
                            medicineName,
                            quantity
                    );

            if (
                    !stockReduced
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Sale saved but stock update failed!"
                );

                return;
            }

            // -------------------------------------------------
            // GET BILL NUMBER
            // -------------------------------------------------

            int billNo =
                    1000
                            +
                            saleDAO
                                    .getTotalSalesCount();

            // -------------------------------------------------
            // SUCCESS MESSAGE
            // -------------------------------------------------

            JOptionPane.showMessageDialog(
                    this,
                    "Sale Completed Successfully!\n\n"
                            +
                            "Medicine: "
                            +
                            medicineName
                            +
                            "\nQuantity: "
                            +
                            quantity
                            +
                            "\nPrice: Rs."
                            +
                            String.format(
                                    "%.2f",
                                    price
                            )
                            +
                            "\nTotal: Rs."
                            +
                            String.format(
                                    "%.2f",
                                    totalAmount
                            )
                            +
                            "\nBill No: "
                            +
                            billNo,
                    "Sale Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // -------------------------------------------------
            // OPEN GUI BILL
            // -------------------------------------------------

            new BillScreen(
                    billNo,
                    medicineName,
                    quantity,
                    price,
                    totalAmount
            );

            // -------------------------------------------------
            // CLEAR + RELOAD
            // -------------------------------------------------

            clearFields();

            loadMedicines();

        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity!"
            );

        } catch (
                Exception e
        ) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Something went wrong while completing sale!\n"
                            +
                            e.getMessage()
            );
        }
    }

    // =========================================================
    // CLEAR FIELDS
    // =========================================================

    private void clearFields() {

        quantityField.setText("");

        totalField.setText("");

        if (
                medicineComboBox.getItemCount()
                        > 0
        ) {

            medicineComboBox.setSelectedIndex(0);

            updateMedicineDetails();
        }
    }

    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setBackground(
                color
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new SalesScreen()
        );
    }
}