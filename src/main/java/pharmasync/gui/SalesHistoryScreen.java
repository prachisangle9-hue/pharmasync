package pharmasync.gui;

import pharmasync.dao.SaleDAO;
import pharmasync.model.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SalesHistoryScreen extends JFrame {

    private JTable salesTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JLabel totalSalesLabel;
    private JLabel totalAmountLabel;

    private SaleDAO saleDAO;

    public SalesHistoryScreen() {

        saleDAO = new SaleDAO();

        setTitle("PharmaSync - Sales History");

        setSize(1000, 650);

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
                new JLabel("Sales History");

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
                new Color(155, 89, 182)
        );

        add(titleLabel);

        // =====================================================
        // DESCRIPTION
        // =====================================================

        JLabel descriptionLabel =
                new JLabel(
                        "View all completed medicine sales"
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
                Color.GRAY
        );

        add(descriptionLabel);

        // =====================================================
        // SEARCH LABEL
        // =====================================================

        JLabel searchLabel =
                new JLabel("Search Medicine:");

        searchLabel.setBounds(
                40,
                115,
                130,
                30
        );

        searchLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        add(searchLabel);

        // =====================================================
        // SEARCH FIELD
        // =====================================================

        searchField =
                new JTextField();

        searchField.setBounds(
                170,
                110,
                300,
                40
        );

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        add(searchField);

        // =====================================================
        // SEARCH BUTTON
        // =====================================================

        JButton searchButton =
                new JButton("Search");

        searchButton.setBounds(
                485,
                110,
                110,
                40
        );

        styleButton(
                searchButton,
                new Color(52, 152, 219)
        );

        add(searchButton);

        // =====================================================
        // REFRESH BUTTON
        // =====================================================

        JButton refreshButton =
                new JButton("Refresh");

        refreshButton.setBounds(
                610,
                110,
                110,
                40
        );

        styleButton(
                refreshButton,
                new Color(46, 204, 113)
        );

        add(refreshButton);

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {

                "Sale ID",
                "Medicine Name",
                "Quantity",
                "Price",
                "Total Amount",
                "Sale Date"
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

        salesTable =
                new JTable(tableModel);

        salesTable.setRowHeight(30);

        salesTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        salesTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        salesTable.getTableHeader()
                .setBackground(
                        new Color(155, 89, 182)
                );

        salesTable.getTableHeader()
                .setForeground(
                        Color.WHITE
                );

        salesTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        salesTable
                );

        scrollPane.setBounds(
                40,
                170,
                900,
                300
        );

        add(scrollPane);

        // =====================================================
        // TOTAL SALES COUNT
        // =====================================================

        JLabel totalSalesText =
                new JLabel("Total Sales:");

        totalSalesText.setBounds(
                40,
                490,
                120,
                30
        );

        totalSalesText.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        add(totalSalesText);

        totalSalesLabel =
                new JLabel("0");

        totalSalesLabel.setBounds(
                150,
                490,
                120,
                30
        );

        totalSalesLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        totalSalesLabel.setForeground(
                new Color(52, 152, 219)
        );

        add(totalSalesLabel);

        // =====================================================
        // TOTAL AMOUNT
        // =====================================================

        JLabel totalAmountText =
                new JLabel("Total Amount:");

        totalAmountText.setBounds(
                300,
                490,
                130,
                30
        );

        totalAmountText.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        add(totalAmountText);

        totalAmountLabel =
                new JLabel("Rs. 0.00");

        totalAmountLabel.setBounds(
                425,
                490,
                180,
                30
        );

        totalAmountLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        totalAmountLabel.setForeground(
                new Color(46, 204, 113)
        );

        add(totalAmountLabel);

        // =====================================================
        // CLOSE BUTTON
        // =====================================================

        JButton closeButton =
                new JButton("Close");

        closeButton.setBounds(
                700,
                485,
                140,
                45
        );

        styleButton(
                closeButton,
                new Color(231, 76, 60)
        );

        add(closeButton);

        // =====================================================
        // LOAD SALES
        // =====================================================

        loadSales();

        // =====================================================
        // SEARCH ACTION
        // =====================================================

        searchButton.addActionListener(
                e -> searchSales()
        );

        searchField.addActionListener(
                e -> searchSales()
        );

        // =====================================================
        // REFRESH ACTION
        // =====================================================

        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadSales();

                }
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
    // LOAD ALL SALES
    // =========================================================

    private void loadSales() {

        tableModel.setRowCount(0);

        ArrayList<Sale> sales =
                saleDAO.getAllSales();

        for (
                Sale sale :
                sales
        ) {

            tableModel.addRow(
                    new Object[]{

                            sale.getSaleId(),

                            sale.getMedicineName(),

                            sale.getQuantity(),

                            String.format(
                                    "Rs. %.2f",
                                    sale.getPrice()
                            ),

                            String.format(
                                    "Rs. %.2f",
                                    sale.getTotalAmount()
                            ),

                            sale.getSaleDate()
                    }
            );
        }

        updateSummary();
    }

    // =========================================================
    // SEARCH SALES
    // =========================================================

    private void searchSales() {

        String keyword =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        if (keyword.isEmpty()) {

            loadSales();

            return;
        }

        tableModel.setRowCount(0);

        ArrayList<Sale> sales =
                saleDAO.getAllSales();

        int count = 0;

        double amount = 0;

        for (
                Sale sale :
                sales
        ) {

            if (
                    sale.getMedicineName()
                            .toLowerCase()
                            .contains(keyword)
            ) {

                tableModel.addRow(
                        new Object[]{

                                sale.getSaleId(),

                                sale.getMedicineName(),

                                sale.getQuantity(),

                                String.format(
                                        "Rs. %.2f",
                                        sale.getPrice()
                                ),

                                String.format(
                                        "Rs. %.2f",
                                        sale.getTotalAmount()
                                ),

                                sale.getSaleDate()
                        }
                );

                count++;

                amount +=
                        sale.getTotalAmount();
            }
        }

        totalSalesLabel.setText(
                String.valueOf(count)
        );

        totalAmountLabel.setText(
                String.format(
                        "Rs. %.2f",
                        amount
                )
        );
    }

    // =========================================================
    // UPDATE SUMMARY
    // =========================================================

    private void updateSummary() {

        int count =
                saleDAO.getTotalSalesCount();

        double amount =
                saleDAO.getTotalSalesAmount();

        totalSalesLabel.setText(
                String.valueOf(count)
        );

        totalAmountLabel.setText(
                String.format(
                        "Rs. %.2f",
                        amount
                )
        );
    }

    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(
            JButton button,
            Color color
    ) {

        button.setBackground(color);

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
                () -> new SalesHistoryScreen()
        );
    }
}