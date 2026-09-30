package pharmasync.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillScreen extends JFrame {

    private JTextArea billArea;

    private int billNo;

    private String medicineName;
    private int quantity;
    private double price;
    private double totalAmount;

    public BillScreen(
            int billNo,
            String medicineName,
            int quantity,
            double price,
            double totalAmount
    ) {

        this.billNo = billNo;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;

        setTitle("PharmaSync - Customer Bill");

        setSize(650, 700);

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
                new JLabel("PHARMASYNC");

        titleLabel.setBounds(
                0,
                20,
                650,
                40
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
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
        // SUBTITLE
        // =====================================================

        JLabel subtitleLabel =
                new JLabel(
                        "Pharmacy Management System"
                );

        subtitleLabel.setBounds(
                0,
                58,
                650,
                25
        );

        subtitleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setForeground(
                Color.GRAY
        );

        add(subtitleLabel);

        // =====================================================
        // BILL AREA
        // =====================================================

        billArea =
                new JTextArea();

        billArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        15
                )
        );

        billArea.setEditable(false);

        billArea.setMargin(
                new Insets(
                        20,
                        20,
                        20,
                        20
                )
        );

        billArea.setBackground(
                new Color(250, 250, 250)
        );

        generateBillText();

        JScrollPane scrollPane =
                new JScrollPane(
                        billArea
                );

        scrollPane.setBounds(
                60,
                100,
                530,
                400
        );

        add(scrollPane);

        // =====================================================
        // PRINT BUTTON
        // =====================================================

        JButton printButton =
                new JButton("Print Bill");

        printButton.setBounds(
                80,
                535,
                140,
                45
        );

        styleButton(
                printButton,
                new Color(52, 152, 219)
        );

        add(printButton);

        // =====================================================
        // SAVE BUTTON
        // =====================================================

        JButton saveButton =
                new JButton("Save Bill");

        saveButton.setBounds(
                250,
                535,
                140,
                45
        );

        styleButton(
                saveButton,
                new Color(46, 204, 113)
        );

        add(saveButton);

        // =====================================================
        // CLOSE BUTTON
        // =====================================================

        JButton closeButton =
                new JButton("Close");

        closeButton.setBounds(
                420,
                535,
                140,
                45
        );

        styleButton(
                closeButton,
                new Color(231, 76, 60)
        );

        add(closeButton);

        // =====================================================
        // PRINT ACTION
        // =====================================================

        printButton.addActionListener(
                e -> printBill()
        );

        // =====================================================
        // SAVE ACTION
        // =====================================================

        saveButton.addActionListener(
                e -> saveBill()
        );

        // =====================================================
        // CLOSE ACTION
        // =====================================================

        closeButton.addActionListener(
                e -> dispose()
        );

        setVisible(true);
    }

    // =========================================================
    // GENERATE BILL TEXT
    // =========================================================

    private void generateBillText() {

        LocalDateTime now =
                LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        String dateTime =
                now.format(formatter);

        StringBuilder bill =
                new StringBuilder();

        bill.append(
                "================================================\n"
        );

        bill.append(
                "                 PHARMASYNC\n"
        );

        bill.append(
                "              CUSTOMER BILL\n"
        );

        bill.append(
                "================================================\n"
        );

        bill.append(
                "Bill No    : "
        );

        bill.append(
                billNo
        );

        bill.append("\n");

        bill.append(
                "Date/Time  : "
        );

        bill.append(
                dateTime
        );

        bill.append("\n");

        bill.append(
                "------------------------------------------------\n"
        );

        bill.append(
                String.format(
                        "%-25s : %s%n",
                        "Medicine",
                        medicineName
                )
        );

        bill.append(
                String.format(
                        "%-25s : %d%n",
                        "Quantity",
                        quantity
                )
        );

        bill.append(
                String.format(
                        "%-25s : Rs. %.2f%n",
                        "Price",
                        price
                )
        );

        bill.append(
                "------------------------------------------------\n"
        );

        bill.append(
                String.format(
                        "%-25s : Rs. %.2f%n",
                        "TOTAL AMOUNT",
                        totalAmount
                )
        );

        bill.append(
                "------------------------------------------------\n"
        );

        bill.append(
                "\n"
        );

        bill.append(
                "              Thank You!\n"
        );

        bill.append(
                "             Visit Again!\n"
        );

        bill.append(
                "\n"
        );

        bill.append(
                "================================================\n"
        );

        billArea.setText(
                bill.toString()
        );
    }

    // =========================================================
    // PRINT BILL
    // =========================================================

    private void printBill() {

        try {

            boolean complete =
                    billArea.print();

            if (complete) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bill printed successfully!",
                        "Print",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Printing was cancelled.",
                        "Print",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (PrinterException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to print bill!\n"
                            + e.getMessage(),
                    "Print Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SAVE BILL
    // =========================================================

    private void saveBill() {

        JFileChooser fileChooser =
                new JFileChooser();

        fileChooser.setDialogTitle(
                "Save Customer Bill"
        );

        fileChooser.setSelectedFile(
                new File(
                        "PharmaSync_Bill_"
                                + billNo
                                + ".txt"
                )
        );

        int result =
                fileChooser.showSaveDialog(
                        this
                );

        if (
                result
                        != JFileChooser.APPROVE_OPTION
        ) {

            return;
        }

        File file =
                fileChooser.getSelectedFile();

        try {

            FileWriter writer =
                    new FileWriter(file);

            writer.write(
                    billArea.getText()
            );

            writer.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Bill saved successfully!\n\n"
                            + file.getAbsolutePath(),
                    "Bill Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save bill!\n"
                            + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
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
                        14
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
                () -> new BillScreen(
                        1001,
                        "Vitamin C",
                        10,
                        30.00,
                        300.00
                )
        );
    }
}