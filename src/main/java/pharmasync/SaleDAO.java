package pharmasync;

import java.sql.*;

public class SaleDAO {

    // 1. Sell Medicine + Automatic Unique Bill Number
    public void sellMedicine(int medicineId, int quantitySold) {

        String checkStockSQL =
                "SELECT medicine_name, price, quantity " +
                        "FROM medicines WHERE medicine_id = ?";

        String updateStockSQL =
                "UPDATE medicines SET quantity = quantity - ? " +
                        "WHERE medicine_id = ?";

        String insertSaleSQL =
                "INSERT INTO sales " +
                        "(medicine_id, quantity_sold, total_amount) " +
                        "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement =
                     connection.prepareStatement(checkStockSQL)) {

            checkStatement.setInt(1, medicineId);

            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                String medicineName =
                        resultSet.getString("medicine_name");

                double price =
                        resultSet.getDouble("price");

                int availableQuantity =
                        resultSet.getInt("quantity");

                if (quantitySold > availableQuantity) {

                    System.out.println(
                            "❌ Not enough stock! Available quantity: "
                                    + availableQuantity
                    );

                    return;
                }

                double totalAmount =
                        price * quantitySold;

                try (PreparedStatement updateStatement =
                             connection.prepareStatement(updateStockSQL);

                     PreparedStatement saleStatement =
                             connection.prepareStatement(
                                     insertSaleSQL,
                                     Statement.RETURN_GENERATED_KEYS)) {

                    // Reduce stock
                    updateStatement.setInt(1, quantitySold);
                    updateStatement.setInt(2, medicineId);

                    updateStatement.executeUpdate();

                    // Save sale record
                    saleStatement.setInt(1, medicineId);
                    saleStatement.setInt(2, quantitySold);
                    saleStatement.setDouble(3, totalAmount);

                    saleStatement.executeUpdate();

                    // Get generated Sale ID
                    ResultSet generatedKeys =
                            saleStatement.getGeneratedKeys();

                    int saleId = 0;

                    if (generatedKeys.next()) {

                        saleId =
                                generatedKeys.getInt(1);
                    }

                    System.out.println(
                            "✅ Sale completed successfully!"
                    );

                    System.out.println(
                            "💰 Total Amount: £"
                                    + totalAmount
                    );

                    System.out.println(
                            "📦 Remaining Stock: "
                                    + (availableQuantity - quantitySold)
                    );

                    AuditLogDAO auditLogDAO = new AuditLogDAO();

                    auditLogDAO.addLog(
                            "admin",
                            "SELL MEDICINE",
                            "Medicine ID " + medicineId +
                                    " sold (Quantity: " + quantitySold + ")"
                    );

                    // Generate Unique Bill Number
                    int billNumber =
                            1000 + saleId;

                    // Create Bill Item
                    BillItem item =
                            new BillItem(
                                    medicineName,
                                    quantitySold,
                                    price
                            );

                    // Create list of bill items
                    java.util.List<BillItem> items =
                            new java.util.ArrayList<>();

                    items.add(item);

                    // Generate Customer Bill
                    BillGenerator billGenerator =
                            new BillGenerator();

                    billGenerator.generateBill(
                            billNumber,
                            items
                    );
                }

            } else {

                System.out.println(
                        "❌ Medicine not found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Sale failed!"
            );

            e.printStackTrace();
        }
    }


    // 2. View Sales History
    public void viewSalesHistory() {

        String sql = """
                SELECT sale_id, medicine_id,
                       quantity_sold, total_amount
                FROM sales
                ORDER BY sale_id DESC
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            System.out.println(
                    "\n----- SALES HISTORY -----"
            );

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.println(
                        "Sale ID: "
                                + resultSet.getInt("sale_id")

                                + " | Medicine ID: "
                                + resultSet.getInt("medicine_id")

                                + " | Quantity Sold: "
                                + resultSet.getInt("quantity_sold")

                                + " | Total Amount: £"
                                + resultSet.getDouble("total_amount")
                );
            }

            if (!found) {

                System.out.println(
                        "No sales found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to fetch sales history!"
            );

            e.printStackTrace();
        }
    }
}