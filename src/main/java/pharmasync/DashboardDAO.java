package pharmasync;

import java.sql.*;

public class DashboardDAO {

    public void showDashboard() {

        String totalMedicinesSQL =
                "SELECT COUNT(*) FROM medicines";

        String totalStockSQL =
                "SELECT COALESCE(SUM(quantity), 0) FROM medicines";

        String lowStockSQL =
                "SELECT COUNT(*) FROM medicines WHERE quantity <= 10";

        String expirySQL =
                "SELECT COUNT(*) FROM medicines " +
                        "WHERE expiry_date <= DATE_ADD(CURDATE(), INTERVAL 30 DAY) " +
                        "AND expiry_date >= CURDATE()";

        String totalSalesSQL =
                "SELECT COALESCE(SUM(total_amount), 0) FROM sales";

        String totalTransactionsSQL =
                "SELECT COUNT(*) FROM sales";

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            int totalMedicines = 0;
            int totalStock = 0;
            int lowStockMedicines = 0;
            int expiringSoon = 0;
            double totalSales = 0;
            int totalTransactions = 0;

            // Total Medicines
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 totalMedicinesSQL);
                 ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    totalMedicines =
                            resultSet.getInt(1);
                }
            }

            // Total Stock
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 totalStockSQL);
                 ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    totalStock =
                            resultSet.getInt(1);
                }
            }

            // Low Stock Medicines
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 lowStockSQL);
                 ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    lowStockMedicines =
                            resultSet.getInt(1);
                }
            }

            // Expiring Soon Medicines
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 expirySQL);
                 ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    expiringSoon =
                            resultSet.getInt(1);
                }
            }

            // Total Sales
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 totalSalesSQL);
                 ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    totalSales =
                            resultSet.getDouble(1);
                }
            }

            // Total Transactions
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 totalTransactionsSQL);
                 ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    totalTransactions =
                            resultSet.getInt(1);
                }
            }

            // Display Dashboard
            System.out.println();
            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "          PHARMASYNC DASHBOARD"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "Total Medicines       : "
                            + totalMedicines
            );

            System.out.println(
                    "Total Stock Units     : "
                            + totalStock
            );

            System.out.println(
                    "Low Stock Medicines   : "
                            + lowStockMedicines
            );

            System.out.println(
                    "Expiring Soon         : "
                            + expiringSoon
            );

            System.out.println(
                    "Total Sales           : £"
                            + String.format(
                            "%.2f",
                            totalSales)
            );

            System.out.println(
                    "Total Transactions    : "
                            + totalTransactions
            );

            System.out.println(
                    "========================================"
            );

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to load dashboard!"
            );

            e.printStackTrace();
        }
    }
}
