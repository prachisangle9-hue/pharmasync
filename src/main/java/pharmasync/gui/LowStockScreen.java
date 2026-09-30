package pharmasync.gui;

import pharmasync.dao.MedicineDAO;
import pharmasync.model.Medicine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class LowStockScreen extends JFrame {

    private JTable lowStockTable;
    private DefaultTableModel tableModel;

    private MedicineDAO medicineDAO;

    // Low stock limit
    private static final int LOW_STOCK_LIMIT = 5;

    public LowStockScreen() {

        medicineDAO = new MedicineDAO();

        // =====================================================
        // FRAME
        // =====================================================

        setTitle("PharmaSync - Low Stock Alert");

        setSize(700, 500);

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
                new JLabel("Low Stock Alert");

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
                new Color(
                        231,
                        76,
                        60
                )
        );

        add(titleLabel);

        // =====================================================
        // DESCRIPTION
        // =====================================================

        JLabel descriptionLabel =
                new JLabel(
                        "Medicines with stock of 5 or less"
                );

        descriptionLabel.setBounds(
                40,
                70,
                400,
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
                new Color(
                        100,
                        100,
                        100
                )
        );

        add(descriptionLabel);

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Medicine Name",
                "Company",
                "Category",
                "Available Stock",
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

        lowStockTable =
                new JTable(
                        tableModel
                );

        lowStockTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        lowStockTable.setRowHeight(30);

        lowStockTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        lowStockTable
                );

        scrollPane.setBounds(
                40,
                115,
                600,
                250
        );

        add(scrollPane);

        // =====================================================
        // REFRESH BUTTON
        // =====================================================

        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.setBounds(
                150,
                395,
                130,
                45
        );

        refreshButton.setBackground(
                new Color(
                        52,
                        152,
                        219
                )
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

        // =====================================================
        // CLOSE BUTTON
        // =====================================================

        JButton closeButton =
                new JButton("Close");

        closeButton.setBounds(
                320,
                395,
                130,
                45
        );

        closeButton.setBackground(
                new Color(
                        231,
                        76,
                        60
                )
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

        // =====================================================
        // LOAD DATA
        // =====================================================

        loadLowStockMedicines();

        // =====================================================
        // REFRESH ACTION
        // =====================================================

        refreshButton.addActionListener(
                e -> loadLowStockMedicines()
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
    // LOAD LOW STOCK MEDICINES
    // =========================================================

    private void loadLowStockMedicines() {

        tableModel.setRowCount(0);

        ArrayList<Medicine> medicines =
                medicineDAO.getAllMedicines();

        int lowStockCount = 0;

        for (
                Medicine medicine :
                medicines
        ) {

            if (
                    medicine.getQuantity()
                            <= LOW_STOCK_LIMIT
                            &&
                            medicine.getQuantity() >= 0
            ) {

                lowStockCount++;

                String status;

                if (
                        medicine.getQuantity() == 0
                ) {

                    status = "OUT OF STOCK";

                } else {

                    status = "LOW STOCK";
                }

                tableModel.addRow(
                        new Object[]{

                                medicine.getMedicineName(),

                                medicine.getCompany(),

                                medicine.getCategory(),

                                medicine.getQuantity(),

                                status
                        }
                );
            }
        }

        // =====================================================
        // NO LOW STOCK MEDICINE
        // =====================================================

        if (
                lowStockCount == 0
        ) {

            JOptionPane.showMessageDialog(
                    this,

                    "No low stock medicines found.",

                    "Stock Status",

                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}