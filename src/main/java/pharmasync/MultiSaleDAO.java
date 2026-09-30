package pharmasync;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultiSaleDAO {

    // Multiple Medicines Sale + One Customer Bill
    public void sellMultipleMedicines(
            int[] medicineIds,
            int[] quantities) {

        if (medicineIds.length != quantities.length) {

            System.out.println(
                    "❌ Medicine IDs and quantities must match!"
            );

            return;
        }

        String checkMedicineSQL =
                "SELECT medicine_name, price, quantity " +
                        "FROM medicines WHERE medicine_id = ?";

        String updateStockSQL =
                "UPDATE medicines SET quantity = quantity - ? " +
                        "WHERE medicine_id = ?";

        String insertSaleSQL =
                "INSERT INTO sales " +
                        "(medicine_id, quantity_sold, total_amount) " +
                        "VALUES (?, ?, ?)";

        List<BillItem> billItems =
                new ArrayList<>();

        double grandTotal = 0;

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            // Transaction start
            connection.setAutoCommit(false);

            for (int i = 0;
                 i < medicineIds.length;
                 i++) {

                int medicineId =
                        medicineIds[i];

                int quantitySold =
                        quantities[i];

                // Check medicine
                try (PreparedStatement checkStatement =
                             connection.prepareStatement(
                                     checkMedicineSQL)) {

                    checkStatement.setInt(
                            1,
                            medicineId
                    );

                    ResultSet resultSet =
                            checkStatement.executeQuery();

                    if (!resultSet.next()) {

                        System.out.println(
                                "❌ Medicine ID "
                                        + medicineId
                                        + " not found!"
                        );

                        connection.rollback();

                        return;
                    }

                    String medicineName =
                            resultSet.getString(
                                    "medicine_name"
                            );

                    double price =
                            resultSet.getDouble(
                                    "price"
                            );

                    int availableQuantity =
                            resultSet.getInt(
                                    "quantity"
                            );

                    // Check stock
                    if (quantitySold >
                            availableQuantity) {

                        System.out.println(
                                "❌ Not enough stock for "
                                        + medicineName
                        );

                        System.out.println(
                                "Available quantity: "
                                        + availableQuantity
                        );

                        connection.rollback();

                        return;
                    }

                    double itemTotal =
                            price * quantitySold;

                    grandTotal =
                            grandTotal + itemTotal;

                    // Add medicine to bill
                    BillItem item =
                            new BillItem(
                                    medicineName,
                                    quantitySold,
                                    price
                            );

                    billItems.add(item);

                    // Reduce stock
                    try (PreparedStatement
                                 updateStatement =
                                 connection.prepareStatement(
                                         updateStockSQL)) {

                        updateStatement.setInt(
                                1,
                                quantitySold
                        );

                        updateStatement.setInt(
                                2,
                                medicineId
                        );

                        updateStatement.executeUpdate();
                    }

                    // Save sale
                    try (PreparedStatement
                                 saleStatement =
                                 connection.prepareStatement(
                                         insertSaleSQL)) {

                        saleStatement.setInt(
                                1,
                                medicineId
                        );

                        saleStatement.setInt(
                                2,
                                quantitySold
                        );

                        saleStatement.setDouble(
                                3,
                                itemTotal
                        );

                        saleStatement.executeUpdate();
                    }
                }
            }

            // Commit all sales
            connection.commit();

            System.out.println(
                    "✅ Multiple medicines sold successfully!"
            );

            System.out.println(
                    "💰 Grand Total: £"
                            + grandTotal
            );

            // Generate one unique bill number
            int billNumber =
                    1000
                            + (int) (System.currentTimeMillis()
                            % 9000);

            BillGenerator billGenerator =
                    new BillGenerator();

            billGenerator.generateBill(
                    billNumber,
                    billItems
            );

        } catch (SQLException e) {

            System.out.println(
                    "❌ Multiple sale failed!"
            );

            e.printStackTrace();
        }
    }
}
