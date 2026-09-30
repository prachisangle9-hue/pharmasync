package pharmasync.gui;

import pharmasync.dao.MedicineDAO;
import pharmasync.database.DBConnection;
import pharmasync.model.Medicine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DashboardScreen extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private final Color SIDEBAR_COLOR =
            new Color(30, 45, 62);

    private final Color SIDEBAR_LIGHT =
            new Color(42, 59, 79);

    private final Color BACKGROUND =
            new Color(245, 247, 250);

    private final Color CARD_BLUE =
            new Color(52, 152, 219);

    private final Color CARD_GREEN =
            new Color(46, 204, 113);

    private final Color CARD_RED =
            new Color(231, 76, 60);

    private final Color CARD_PURPLE =
            new Color(155, 89, 182);

    private final Color ORANGE =
            new Color(230, 126, 34);

    private final Color DARK_BLUE =
            new Color(41, 128, 185);

    private final Color TEXT_DARK =
            new Color(44, 62, 80);

    private final Color TEXT_GRAY =
            new Color(110, 120, 130);


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JPanel mainPanel;

    private JTable medicineTable;

    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JLabel totalMedicinesLabel;

    private JLabel totalStockLabel;

    private JLabel lowStockLabel;

    private JLabel totalSalesLabel;

    private JLabel statusLabel;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DashboardScreen() {

        setTitle("PharmaSync Dashboard");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1100, 700)
        );

        setLayout(
                new BorderLayout()
        );

        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar =
                createSidebar();

        add(
                sidebar,
                BorderLayout.WEST
        );


        // =====================================================
        // MAIN CONTENT
        // =====================================================

        mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // TOP HEADER
        // =====================================================

        JPanel header =
                createHeader();

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // CENTER CONTENT
        // =====================================================

        JPanel content =
                createMainContent();

        JScrollPane contentScroll =
                new JScrollPane(
                        content
                );

        contentScroll.setBorder(null);

        contentScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        contentScroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        mainPanel.add(
                contentScroll,
                BorderLayout.CENTER
        );


        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                createFooter();

        mainPanel.add(
                footer,
                BorderLayout.SOUTH
        );


        // =====================================================
        // LOAD DATA
        // =====================================================

        loadMedicines();


        // =====================================================
        // FULL SCREEN
        // =====================================================

        setLocationRelativeTo(null);

        setExtendedState(
                JFrame.MAXIMIZED_BOTH
        );

        setVisible(true);
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel();

        sidebar.setPreferredSize(
                new Dimension(240, 0)
        );

        sidebar.setBackground(
                SIDEBAR_COLOR
        );

        sidebar.setLayout(
                new BorderLayout()
        );


        // -----------------------------------------------------
        // LOGO PANEL
        // -----------------------------------------------------

        JPanel logoPanel =
                new JPanel();

        logoPanel.setBackground(
                SIDEBAR_COLOR
        );

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBorder(
                new EmptyBorder(
                        30,
                        25,
                        25,
                        20
                )
        );


        JLabel logo =
                new JLabel(
                        "PharmaSync"
                );

        logo.setForeground(
                Color.WHITE
        );

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        logoPanel.add(
                logo
        );


        JLabel subtitle =
                new JLabel(
                        "Pharmacy Management"
                );

        subtitle.setForeground(
                new Color(
                        190,
                        200,
                        210
                )
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        0,
                        0
                )
        );

        logoPanel.add(
                subtitle
        );

        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );


        // -----------------------------------------------------
        // MENU
        // -----------------------------------------------------

        JPanel menuPanel =
                new JPanel();

        menuPanel.setBackground(
                SIDEBAR_COLOR
        );

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );


        JButton dashboardButton =
                createMenuButton(
                        "Dashboard"
                );

        JButton medicinesButton =
                createMenuButton(
                        "Medicines"
                );

        JButton salesButton =
                createMenuButton(
                        "Sales & Billing"
                );

        JButton refreshButton =
                createMenuButton(
                        "Refresh"
                );

        JButton expiryButton =
                createMenuButton(
                        "Expiry Alert"
                );

        JButton lowStockButton =
                createMenuButton(
                        "Low Stock Alert"
                );

        JButton historyButton =
                createMenuButton(
                        "Sales History"
                );


        menuPanel.add(
                dashboardButton
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                medicinesButton
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                salesButton
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                refreshButton
        );

        menuPanel.add(
                Box.createVerticalStrut(25)
        );

        menuPanel.add(
                expiryButton
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                lowStockButton
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                historyButton
        );


        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );


        // -----------------------------------------------------
        // LOGOUT
        // -----------------------------------------------------

        JPanel logoutPanel =
                new JPanel(
                        new BorderLayout()
                );

        logoutPanel.setBackground(
                SIDEBAR_COLOR
        );

        logoutPanel.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        20,
                        15
                )
        );


        JButton logoutButton =
                createMenuButton(
                        "Logout"
                );

        logoutButton.setBackground(
                new Color(
                        192,
                        57,
                        43
                )
        );


        logoutPanel.add(
                logoutButton,
                BorderLayout.CENTER
        );


        sidebar.add(
                logoutPanel,
                BorderLayout.SOUTH
        );


        // -----------------------------------------------------
        // ACTIONS
        // -----------------------------------------------------

        dashboardButton.addActionListener(
                e -> loadMedicines()
        );


        medicinesButton.addActionListener(
                e -> loadMedicines()
        );


        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadMedicines();

                }
        );


        salesButton.addActionListener(
                e -> new SalesScreen()
        );


        expiryButton.addActionListener(
                e -> new ExpiryAlertScreen()
        );


        lowStockButton.addActionListener(
                e -> new LowStockScreen()
        );


        historyButton.addActionListener(
                e -> new SalesHistoryScreen()
        );


        logoutButton.addActionListener(
                e -> logout()
        );


        return sidebar;
    }


    // =========================================================
    // MENU BUTTON
    // =========================================================

    private JButton createMenuButton(
            String text
    ) {

        JButton button =
                new JButton(
                        text
                );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        button.setPreferredSize(
                new Dimension(
                        205,
                        48
                )
        );

        button.setMinimumSize(
                new Dimension(
                        205,
                        48
                )
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setBackground(
                SIDEBAR_LIGHT
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

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setBorder(
                new EmptyBorder(
                        0,
                        18,
                        0,
                        10
                )
        );

        button.setFocusPainted(
                false
        );

        button.setBorderPainted(
                false
        );

        return button;
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                Color.WHITE
        );

        header.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        20,
                        35
                )
        );


        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(
                false
        );

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "PharmaSync Dashboard"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                TEXT_DARK
        );


        JLabel subtitle =
                new JLabel(
                        "Pharmacy Management System"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                TEXT_GRAY
        );

        subtitle.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        0,
                        0
                )
        );


        titlePanel.add(
                title
        );

        titlePanel.add(
                subtitle
        );


        JLabel systemLabel =
                new JLabel(
                        "SYSTEM ONLINE"
                );

        systemLabel.setOpaque(
                true
        );

        systemLabel.setBackground(
                new Color(
                        232,
                        245,
                        233
                )
        );

        systemLabel.setForeground(
                new Color(
                        39,
                        125,
                        66
                )
        );

        systemLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        systemLabel.setBorder(
                new EmptyBorder(
                        8,
                        14,
                        8,
                        14
                )
        );


        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                systemLabel,
                BorderLayout.EAST
        );


        return header;
    }


    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private JPanel createMainContent() {

        JPanel content =
                new JPanel();

        content.setBackground(
                BACKGROUND
        );

        content.setBorder(
                new EmptyBorder(
                        0,
                        35,
                        25,
                        35
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );


        // =====================================================
        // STATISTICS TITLE
        // =====================================================

        JLabel overviewLabel =
                new JLabel(
                        "Overview"
                );

        overviewLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        overviewLabel.setForeground(
                TEXT_DARK
        );

        overviewLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(
                overviewLabel
        );

        content.add(
                Box.createVerticalStrut(12)
        );


        // =====================================================
        // STAT CARDS
        // =====================================================

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                18,
                                0
                        )
                );

        cardsPanel.setOpaque(
                false
        );

        cardsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        125
                )
        );

        cardsPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JPanel medicineCard =
                createStatCard(
                        "Total Medicines",
                        "0",
                        CARD_BLUE
                );

        totalMedicinesLabel =
                (JLabel)
                        medicineCard.getClientProperty(
                                "valueLabel"
                        );


        JPanel stockCard =
                createStatCard(
                        "Total Stock",
                        "0",
                        CARD_GREEN
                );

        totalStockLabel =
                (JLabel)
                        stockCard.getClientProperty(
                                "valueLabel"
                        );


        JPanel lowStockCard =
                createStatCard(
                        "Low Stock",
                        "0",
                        CARD_RED
                );

        lowStockLabel =
                (JLabel)
                        lowStockCard.getClientProperty(
                                "valueLabel"
                        );


        JPanel salesCard =
                createStatCard(
                        "Total Sales",
                        "Rs.0.00",
                        CARD_PURPLE
                );

        totalSalesLabel =
                (JLabel)
                        salesCard.getClientProperty(
                                "valueLabel"
                        );


        cardsPanel.add(
                medicineCard
        );

        cardsPanel.add(
                stockCard
        );

        cardsPanel.add(
                lowStockCard
        );

        cardsPanel.add(
                salesCard
        );


        content.add(
                cardsPanel
        );


        content.add(
                Box.createVerticalStrut(25)
        );


        // =====================================================
        // QUICK ACTIONS
        // =====================================================

        JLabel quickLabel =
                new JLabel(
                        "Quick Actions"
                );

        quickLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        quickLabel.setForeground(
                TEXT_DARK
        );

        quickLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(
                quickLabel
        );

        content.add(
                Box.createVerticalStrut(12)
        );


        JPanel actionsPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                12,
                                12
                        )
                );

        actionsPanel.setOpaque(
                false
        );

        actionsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );

        actionsPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JButton addButton =
                createActionButton(
                        "Add Medicine",
                        CARD_GREEN
                );


        JButton editButton =
                createActionButton(
                        "Edit Medicine",
                        CARD_BLUE
                );


        JButton deleteButton =
                createActionButton(
                        "Delete Medicine",
                        CARD_RED
                );


        JButton salesButton =
                createActionButton(
                        "Sales & Billing",
                        CARD_PURPLE
                );


        JButton refreshButton =
                createActionButton(
                        "Refresh",
                        TEXT_DARK
                );


        JButton expiryButton =
                createActionButton(
                        "Expiry Alert",
                        ORANGE
                );


        JButton lowStockButton =
                createActionButton(
                        "Low Stock Alert",
                        CARD_RED
                );


        JButton historyButton =
                createActionButton(
                        "Sales History",
                        CARD_PURPLE
                );


        actionsPanel.add(
                addButton
        );

        actionsPanel.add(
                editButton
        );

        actionsPanel.add(
                deleteButton
        );

        actionsPanel.add(
                salesButton
        );

        actionsPanel.add(
                refreshButton
        );

        actionsPanel.add(
                expiryButton
        );

        actionsPanel.add(
                lowStockButton
        );

        actionsPanel.add(
                historyButton
        );

        content.add(
                actionsPanel
        );


        content.add(
                Box.createVerticalStrut(25)
        );


        // =====================================================
        // SEARCH PANEL
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        searchPanel.setBackground(
                Color.WHITE
        );

        searchPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        228,
                                        232
                                )
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        searchPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        75
                )
        );

        searchPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel searchLabel =
                new JLabel(
                        "Search Medicine"
                );

        searchLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        searchLabel.setForeground(
                TEXT_DARK
        );


        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        searchField.setPreferredSize(
                new Dimension(
                        300,
                        40
                )
        );


        JButton searchButton =
                new JButton(
                        "Search"
                );

        styleButton(
                searchButton,
                CARD_BLUE
        );

        searchButton.setPreferredSize(
                new Dimension(
                        110,
                        40
                )
        );


        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );


        content.add(
                searchPanel
        );


        content.add(
                Box.createVerticalStrut(20)
        );


        // =====================================================
        // TABLE TITLE
        // =====================================================

        JPanel tableTitlePanel =
                new JPanel(
                        new BorderLayout()
                );

        tableTitlePanel.setOpaque(
                false
        );

        tableTitlePanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        35
                )
        );

        tableTitlePanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel medicineListLabel =
                new JLabel(
                        "Medicine Inventory"
                );

        medicineListLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        medicineListLabel.setForeground(
                TEXT_DARK
        );


        tableTitlePanel.add(
                medicineListLabel,
                BorderLayout.WEST
        );


        content.add(
                tableTitlePanel
        );


        content.add(
                Box.createVerticalStrut(10)
        );


        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {

                "Medicine Name",
                "Company",
                "Category",
                "Quantity",
                "Price",
                "Expiry Date"

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


        medicineTable =
                new JTable(
                        tableModel
                );


        medicineTable.setRowHeight(
                36
        );

        medicineTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        medicineTable.setForeground(
                TEXT_DARK
        );

        medicineTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        medicineTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );


        medicineTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                14
                        )
                );


        medicineTable.getTableHeader()
                .setBackground(
                        CARD_BLUE
                );


        medicineTable.getTableHeader()
                .setForeground(
                        Color.WHITE
                );


        // Center quantity and price
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        medicineTable
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        centerRenderer
                );


        medicineTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        centerRenderer
                );


        JScrollPane tableScroll =
                new JScrollPane(
                        medicineTable
                );


        tableScroll.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        tableScroll.setPreferredSize(
                new Dimension(
                        900,
                        400
                )
        );


        tableScroll.setMinimumSize(
                new Dimension(
                        700,
                        300
                )
        );


        tableScroll.setBorder(
                BorderFactory.createLineBorder(
                        new Color(
                                220,
                                224,
                                230
                        )
                )
        );


        content.add(
                tableScroll
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================


        addButton.addActionListener(
                e -> new AddMedicineScreen()
        );


        editButton.addActionListener(
                e -> editSelectedMedicine()
        );


        deleteButton.addActionListener(
                e -> deleteSelectedMedicine()
        );


        salesButton.addActionListener(
                e -> new SalesScreen()
        );


        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadMedicines();

                }
        );


        expiryButton.addActionListener(
                e -> new ExpiryAlertScreen()
        );


        lowStockButton.addActionListener(
                e -> new LowStockScreen()
        );


        historyButton.addActionListener(
                e -> new SalesHistoryScreen()
        );


        searchButton.addActionListener(
                e -> searchMedicines()
        );


        searchField.addActionListener(
                e -> searchMedicines()
        );


        return content;
    }


    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            Color color
    ) {

        JPanel card =
                new JPanel();

        card.setBackground(
                color
        );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        15,
                        20
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setForeground(
                Color.WHITE
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );


        JLabel valueLabel =
                new JLabel(
                        value
                );

        valueLabel.setForeground(
                Color.WHITE
        );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );


        card.putClientProperty(
                "valueLabel",
                valueLabel
        );


        return card;
    }


    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private JButton createActionButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(
                        text
                );

        styleButton(
                button,
                color
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        return button;
    }


    // =========================================================
    // STYLE BUTTON
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

        button.setFocusPainted(
                false
        );

        button.setBorderPainted(
                false
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(
                Color.WHITE
        );

        footer.setBorder(
                new EmptyBorder(
                        8,
                        25,
                        8,
                        25
                )
        );


        statusLabel =
                new JLabel(
                        "Ready"
                );

        statusLabel.setForeground(
                TEXT_GRAY
        );

        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        JLabel copyright =
                new JLabel(
                        "PharmaSync • Pharmacy Management System"
                );

        copyright.setForeground(
                TEXT_GRAY
        );

        copyright.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        footer.add(
                statusLabel,
                BorderLayout.WEST
        );

        footer.add(
                copyright,
                BorderLayout.EAST
        );


        return footer;
    }


    // =========================================================
    // LOAD MEDICINES
    // =========================================================

    private void loadMedicines() {

        MedicineDAO dao =
                new MedicineDAO();


        ArrayList<Medicine> list =
                dao.getAllMedicines();


        tableModel.setRowCount(
                0
        );


        for (
                Medicine medicine :
                list
        ) {

            tableModel.addRow(
                    new Object[]{

                            medicine.getMedicineName(),

                            medicine.getCompany(),

                            medicine.getCategory(),

                            medicine.getQuantity(),

                            String.format(
                                    "Rs. %.2f",
                                    medicine.getPrice()
                            ),

                            medicine.getExpiryDate()

                    }
            );
        }


        updateStatistics(
                list
        );


        if (statusLabel != null) {

            statusLabel.setText(
                    "Inventory refreshed • "
                            +
                            list.size()
                            +
                            " medicines"
            );
        }
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private void searchMedicines() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (
                keyword.isEmpty()
        ) {

            loadMedicines();

            return;
        }


        MedicineDAO dao =
                new MedicineDAO();


        ArrayList<Medicine> list =
                dao.searchMedicine(
                        keyword
                );


        tableModel.setRowCount(
                0
        );


        for (
                Medicine medicine :
                list
        ) {

            tableModel.addRow(
                    new Object[]{

                            medicine.getMedicineName(),

                            medicine.getCompany(),

                            medicine.getCategory(),

                            medicine.getQuantity(),

                            String.format(
                                    "Rs. %.2f",
                                    medicine.getPrice()
                            ),

                            medicine.getExpiryDate()

                    }
            );
        }


        if (statusLabel != null) {

            statusLabel.setText(
                    "Search results: "
                            +
                            list.size()
                            +
                            " medicine(s)"
            );
        }
    }


    // =========================================================
    // EDIT MEDICINE
    // =========================================================

    private void editSelectedMedicine() {

        int selectedRow =
                medicineTable.getSelectedRow();


        if (
                selectedRow == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first!",
                    "Edit Medicine",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String medicineName =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();


        String company =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();


        String category =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString();


        String quantity =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString();


        String price =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                4
                        )
                        .toString()
                        .replace(
                                "Rs. ",
                                ""
                        );


        String expiryDate =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                5
                        )
                        .toString();


        new EditMedicineScreen(
                medicineName,
                company,
                category,
                quantity,
                price,
                expiryDate
        );
    }


    // =========================================================
    // DELETE MEDICINE
    // =========================================================

    private void deleteSelectedMedicine() {

        int selectedRow =
                medicineTable.getSelectedRow();


        if (
                selectedRow == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first!",
                    "Delete Medicine",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String medicineName =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();


        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                +
                                medicineName
                                +
                                "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                confirm
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }


        MedicineDAO dao =
                new MedicineDAO();


        boolean deleted =
                dao.deleteMedicine(
                        medicineName
                );


        if (deleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine Deleted Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            loadMedicines();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete medicine!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics(
            ArrayList<Medicine> list
    ) {

        int totalMedicines =
                list.size();


        int totalStock = 0;


        int lowStock = 0;


        for (
                Medicine medicine :
                list
        ) {

            totalStock +=
                    medicine.getQuantity();


            // Same threshold as LowStockScreen
            if (
                    medicine.getQuantity()
                            <= 5
            ) {

                lowStock++;
            }
        }


        totalMedicinesLabel.setText(
                String.valueOf(
                        totalMedicines
                )
        );


        totalStockLabel.setText(
                String.valueOf(
                        totalStock
                )
        );


        lowStockLabel.setText(
                String.valueOf(
                        lowStock
                )
        );


        double totalSales =
                getTotalSales();


        totalSalesLabel.setText(
                "Rs."
                        +
                        String.format(
                                "%.2f",
                                totalSales
                        )
        );
    }


    // =========================================================
    // TOTAL SALES
    // =========================================================

    private double getTotalSales() {

        double total = 0;


        String sql =
                "SELECT COALESCE("
                        +
                        "SUM(total_amount), 0"
                        +
                        ") "
                        +
                        "FROM sales";


        try {

            Connection con =
                    DBConnection.getConnection();


            if (
                    con == null
            ) {

                return 0;
            }


            PreparedStatement pst =
                    con.prepareStatement(
                            sql
                    );


            ResultSet rs =
                    pst.executeQuery();


            if (
                    rs.next()
            ) {

                total =
                        rs.getDouble(1);
            }


            rs.close();

            pst.close();

            con.close();


        } catch (
                Exception e
        ) {

            System.out.println(
                    "Sales statistics error: "
                            +
                            e.getMessage()
            );
        }


        return total;
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result
                        == JOptionPane.YES_OPTION
        ) {

            dispose();

            new LoginScreen();
        }
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (
                            Exception ignored
                    ) {
                    }


                    new DashboardScreen();
                }
        );
    }
}