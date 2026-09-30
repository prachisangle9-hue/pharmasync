package pharmasync.gui;

import pharmasync.dao.MedicineDAO;
import pharmasync.model.Medicine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ExpiryAlertScreen extends JFrame {

    private JTable expiryTable;
    private DefaultTableModel tableModel;

    private MedicineDAO medicineDAO;

    private static final int EXPIRING_SOON_DAYS = 30;

    public ExpiryAlertScreen() {

        medicineDAO = new MedicineDAO();

        setTitle("PharmaSync - Expiry Alert");

        setSize(850, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel("Expiry Alert");

        titleLabel.setBounds(
                40,
                25,
                400,
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
                new Color(231, 76, 60)
        );

        add(titleLabel);

        // =========================
        // DESCRIPTION
        // =========================

        JLabel descriptionLabel =
                new JLabel(
                        "Medicine expiry status and alerts"
                );

        descriptionLabel.setBounds(
                40,
                70,
                500,
                30
        );

        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        descriptionLabel.setForeground(
                new Color(100, 100, 100)
        );

        add(descriptionLabel);

        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "Medicine Name",
                "Company",
                "Category",
                "Expiry Date",
                "Days Remaining",
                "Status"
        };

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };

        expiryTable =
                new JTable(tableModel);

        expiryTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        expiryTable.setRowHeight(30);

        expiryTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        expiryTable.getTableHeader()
                .setBackground(
                        new Color(52, 152, 219)
                );

        expiryTable.getTableHeader()
                .setForeground(Color.WHITE);

        JScrollPane scrollPane =
                new JScrollPane(expiryTable);

        scrollPane.setBounds(
                40,
                115,
                750,
                280
        );

        add(scrollPane);

        // =========================
        // REFRESH BUTTON
        // =========================

        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.setBounds(
                230,
                425,
                140,
                45
        );

        refreshButton.setBackground(
                new Color(52, 152, 219)
        );

        refreshButton.setForeground(
                Color.WHITE
        );

        refreshButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        refreshButton.setFocusPainted(false);

        add(refreshButton);

        // =========================
        // CLOSE BUTTON
        // =========================

        JButton closeButton =
                new JButton("Close");

        closeButton.setBounds(
                390,
                425,
                140,
                45
        );

        closeButton.setBackground(
                new Color(231, 76, 60)
        );

        closeButton.setForeground(
                Color.WHITE
        );

        closeButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        closeButton.setFocusPainted(false);

        add(closeButton);

        // =========================
        // LOAD DATA
        // =========================

        loadExpiryData();

        // =========================
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> loadExpiryData()
        );

        closeButton.addActionListener(
                e -> dispose()
        );

        setVisible(true);
    }

    // =====================================================
    // LOAD EXPIRY DATA
    // =====================================================

    private void loadExpiryData() {

        tableModel.setRowCount(0);

        ArrayList<Medicine> medicines =
                medicineDAO.getAllMedicines();

        LocalDate today =
                LocalDate.now();

        int expiredCount = 0;

        int expiringSoonCount = 0;

        int validCount = 0;

        int invalidCount = 0;

        for (
                Medicine medicine :
                medicines
        ) {

            String expiryText =
                    medicine.getExpiryDate();

            if (
                    expiryText == null
                            ||
                            expiryText.trim().isEmpty()
            ) {

                tableModel.addRow(
                        new Object[]{

                                medicine.getMedicineName(),

                                medicine.getCompany(),

                                medicine.getCategory(),

                                "Not Available",

                                "-",

                                "INVALID DATE"
                        }
                );

                invalidCount++;

                continue;
            }

            LocalDate expiryDate =
                    parseExpiryDate(
                            expiryText
                    );

            // =========================
            // INVALID DATE
            // =========================

            if (expiryDate == null) {

                tableModel.addRow(
                        new Object[]{

                                medicine.getMedicineName(),

                                medicine.getCompany(),

                                medicine.getCategory(),

                                expiryText,

                                "-",

                                "INVALID DATE"
                        }
                );

                invalidCount++;

                continue;
            }

            // =========================
            // CALCULATE DAYS
            // =========================

            long daysRemaining =
                    ChronoUnit.DAYS.between(
                            today,
                            expiryDate
                    );

            String status;

            // =========================
            // EXPIRED
            // =========================

            if (daysRemaining < 0) {

                status = "EXPIRED";

                expiredCount++;
            }

            // =========================
            // EXPIRING SOON
            // =========================

            else if (
                    daysRemaining
                            <= EXPIRING_SOON_DAYS
            ) {

                status = "EXPIRING SOON";

                expiringSoonCount++;
            }

            // =========================
            // VALID
            // =========================

            else {

                status = "VALID";

                validCount++;
            }

            // =========================
            // ADD ROW
            // =========================

            tableModel.addRow(
                    new Object[]{

                            medicine.getMedicineName(),

                            medicine.getCompany(),

                            medicine.getCategory(),

                            expiryDate.toString(),

                            daysRemaining,

                            status
                    }
            );
        }

        // =========================
        // SUMMARY MESSAGE
        // =========================

        String message =
                "Expired: "
                        + expiredCount
                        + "    |    "
                        + "Expiring Soon: "
                        + expiringSoonCount
                        + "    |    "
                        + "Valid: "
                        + validCount
                        + "    |    "
                        + "Invalid: "
                        + invalidCount;

        JLabel summaryLabel =
                new JLabel(message);

        summaryLabel.setBounds(
                40,
                395,
                750,
                25
        );

        summaryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        summaryLabel.setForeground(
                new Color(70, 70, 70)
        );

        add(summaryLabel);

        revalidate();

        repaint();
    }

    // =====================================================
    // PARSE EXPIRY DATE
    // =====================================================

    private LocalDate parseExpiryDate(
            String dateText
    ) {

        String value =
                dateText.trim();

        // =========================
        // FORMAT 1: DD-MM-YYYY
        // =========================

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy"
                    );

            return LocalDate.parse(
                    value,
                    formatter
            );

        } catch (DateTimeParseException ignored) {
        }

        // =========================
        // FORMAT 2: YYYY-MM-DD
        // =========================

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "yyyy-MM-dd"
                    );

            return LocalDate.parse(
                    value,
                    formatter
            );

        } catch (DateTimeParseException ignored) {
        }

        // =========================
        // FORMAT 3: DD/MM/YYYY
        // =========================

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd/MM/yyyy"
                    );

            return LocalDate.parse(
                    value,
                    formatter
            );

        } catch (DateTimeParseException ignored) {
        }

        return null;
    }

    // =====================================================
    // MAIN METHOD
    // =====================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new ExpiryAlertScreen()
        );
    }
}