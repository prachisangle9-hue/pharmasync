package pharmasync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicineDAO {
    private boolean isBatchNumberExists(String batchNo) {

        String sql = "SELECT COUNT(*) FROM medicines WHERE batch_no = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, batchNo);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean isBarcodeExists(String barcode) {

        String sql = "SELECT COUNT(*) FROM medicines WHERE barcode = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, barcode);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 1. Add Medicine
    public void addMedicine(
            String medicineName,
            String company,
            String category,
            String batchNo,
            String manufactureDate,
            String expiryDate,
            double price,
            int quantity,
            String barcode) {

        // Input Validation
        if (InputValidator.isEmpty(medicineName)) {
            System.out.println("❌ Medicine name cannot be empty!");
            return;
        }

        if (InputValidator.isEmpty(company)) {
            System.out.println("❌ Company name cannot be empty!");
            return;
        }

        if (InputValidator.isEmpty(category)) {
            System.out.println("❌ Category cannot be empty!");
            return;
        }

        if (InputValidator.isEmpty(batchNo)) {
            System.out.println("❌ Batch number cannot be empty!");
            return;
        }

        if (isBatchNumberExists(batchNo.trim())) {
            System.out.println("❌ Batch Number already exists!");
            return;
        }

        if (InputValidator.isEmpty(manufactureDate)) {
            System.out.println("❌ Manufacture date cannot be empty!");
            return;
        }

        if (!InputValidator.isValidDate(manufactureDate)) {
            System.out.println("❌ Invalid manufacture date! Use YYYY-MM-DD");
            return;
        }

        if (InputValidator.isEmpty(expiryDate)) {
            System.out.println("❌ Expiry date cannot be empty!");
            return;
        }

        if (!InputValidator.isValidDate(expiryDate)) {
            System.out.println("❌ Invalid expiry date! Use YYYY-MM-DD");
            return;
        }

        if (!InputValidator.isExpiryAfterManufacture(
                manufactureDate,
                expiryDate)) {

            System.out.println(
                    "❌ Expiry date must be after manufacture date!"
            );

            return;
        }

        if (!InputValidator.isPositivePrice(price)) {
            System.out.println("❌ Price must be greater than 0!");
            return;
        }

        if (!InputValidator.isPositiveQuantity(quantity)) {
            System.out.println("❌ Quantity must be greater than 0!");
            return;
        }

        if (InputValidator.isEmpty(barcode)) {
            System.out.println("❌ Barcode cannot be empty!");
            return;
        }

        if (isBarcodeExists(barcode.trim())) {
            System.out.println("❌ Barcode already exists!");
            return;
        }

        String sql = """
                INSERT INTO medicines
                (medicine_name, company, category, batch_no,
                 manufacture_date, expiry_date, price, quantity, barcode)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, medicineName.trim());
            statement.setString(2, company.trim());
            statement.setString(3, category.trim());
            statement.setString(4, batchNo.trim());
            statement.setString(5, manufactureDate.trim());
            statement.setString(6, expiryDate.trim());
            statement.setDouble(7, price);
            statement.setInt(8, quantity);
            statement.setString(9, barcode.trim());

            statement.executeUpdate();

            AuditLogDAO auditLogDAO = new AuditLogDAO();

            auditLogDAO.addLog(
                    "admin",
                    "ADD MEDICINE",
                    "Medicine '" + medicineName + "' added successfully"
            );

            System.out.println(
                    "✅ Medicine added successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to add medicine!"
            );

            e.printStackTrace();
        }
    }


    // 2. View All Medicines
    public void viewAllMedicines() {

        String sql = """
    SELECT *
    FROM medicines
    WHERE status = 'ACTIVE'
    """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            System.out.println(
                    "\n----- ALL MEDICINES -----"
            );

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.println(
                        "ID: "
                                + resultSet.getInt("medicine_id")
                                + " | Name: "
                                + resultSet.getString("medicine_name")
                                + " | Company: "
                                + resultSet.getString("company")
                                + " | Category: "
                                + resultSet.getString("category")
                                + " | Batch No: "
                                + resultSet.getString("batch_no")
                                + " | Price: "
                                + resultSet.getDouble("price")
                                + " | Quantity: "
                                + resultSet.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println(
                        "No medicines found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to fetch medicines!"
            );

            e.printStackTrace();
        }
    }


    // 3. Search Medicine
    public void searchMedicine(
            String medicineName) {

        if (InputValidator.isEmpty(medicineName)) {

            System.out.println(
                    "❌ Medicine name cannot be empty!"
            );

            return;
        }

        String sql = """
                SELECT * FROM medicines
                WHERE medicine_name LIKE ?
                AND status = 'ACTIVE'
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    "%" + medicineName.trim() + "%"
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                System.out.println(
                        "\n----- SEARCH RESULTS -----"
                );

                boolean found = false;

                while (resultSet.next()) {

                    found = true;

                    System.out.println(
                            "ID: "
                                    + resultSet.getInt(
                                    "medicine_id")
                                    + " | Name: "
                                    + resultSet.getString(
                                    "medicine_name")
                                    + " | Company: "
                                    + resultSet.getString(
                                    "company")
                                    + " | Category: "
                                    + resultSet.getString(
                                    "category")
                                    + " | Batch No: "
                                    + resultSet.getString(
                                    "batch_no")
                                    + " | Price: "
                                    + resultSet.getDouble(
                                    "price")
                                    + " | Quantity: "
                                    + resultSet.getInt(
                                    "quantity")
                    );
                }

                if (!found) {

                    System.out.println(
                            "No medicine found!"
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to search medicine!"
            );

            e.printStackTrace();
        }
    }


    // 4. Update Medicine
    public void updateMedicine(
            int medicineId,
            double newPrice,
            int newQuantity) {

        // Validation
        if (!InputValidator.isValidId(medicineId)) {

            System.out.println(
                    "❌ Invalid medicine ID!"
            );

            return;
        }

        if (!InputValidator.isPositivePrice(newPrice)) {

            System.out.println(
                    "❌ Price must be greater than 0!"
            );

            return;
        }

        if (!InputValidator.isPositiveQuantity(newQuantity)) {

            System.out.println(
                    "❌ Quantity must be greater than 0!"
            );

            return;
        }

        String sql = """
                UPDATE medicines
                SET price = ?, quantity = ?
                WHERE medicine_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, newPrice);
            statement.setInt(2, newQuantity);
            statement.setInt(3, medicineId);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                AuditLogDAO auditLogDAO = new AuditLogDAO();

                auditLogDAO.addLog(
                        "admin",
                        "UPDATE MEDICINE",
                        "Medicine ID " + medicineId + " updated successfully"
                );

                System.out.println(
                        "✅ Medicine updated successfully!"
                );

            } else {

                System.out.println(
                        "❌ Medicine not found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to update medicine!"
            );

            e.printStackTrace();
        }
    }


    // 5. Delete Medicine
    public void deleteMedicine(
            int medicineId) {

        if (!InputValidator.isValidId(medicineId)) {

            System.out.println(
                    "❌ Invalid medicine ID!"
            );

            return;
        }

        String sql = """
    UPDATE medicines
    SET status = 'INACTIVE'
    WHERE medicine_id = ?
    """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, medicineId);

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                AuditLogDAO auditLogDAO = new AuditLogDAO();

                auditLogDAO.addLog(
                        "admin",
                        "DELETE MEDICINE",
                        "Medicine ID " + medicineId + " marked as INACTIVE"
                );

                System.out.println(
                        "✅ Medicine marked as INACTIVE successfully!"
                );

            } else {

                System.out.println(
                        "❌ Medicine not found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to delete medicine!"
            );

            e.printStackTrace();
        }
    }


    // 6. Expiry Alert System
    public void checkExpiryAlerts() {

        String sql = """
                SELECT medicine_id, medicine_name,
                       batch_no, expiry_date
                FROM medicines
                WHERE expiry_date <=
                      DATE_ADD(CURDATE(),
                      INTERVAL 30 DAY)
                ORDER BY expiry_date
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            System.out.println(
                    "\n----- EXPIRY ALERTS -----"
            );

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                String medicineName =
                        resultSet.getString(
                                "medicine_name"
                        );

                String batchNo =
                        resultSet.getString(
                                "batch_no"
                        );

                String expiryDate =
                        resultSet.getString(
                                "expiry_date"
                        );

                System.out.println(
                        "⚠️ Medicine: "
                                + medicineName
                                + " | Batch: "
                                + batchNo
                                + " | Expiry Date: "
                                + expiryDate
                );
            }

            if (!found) {

                System.out.println(
                        "✅ No expiry alerts!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to check expiry alerts!"
            );

            e.printStackTrace();
        }
    }


    // 7. Low Stock Alert System
    public void checkLowStockAlerts() {

        String sql = """
                SELECT medicine_id, medicine_name,
                       company, quantity
                FROM medicines
                WHERE quantity <= 10
                ORDER BY quantity ASC
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            System.out.println(
                    "\n----- LOW STOCK ALERTS -----"
            );

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.println(
                        "⚠️ LOW STOCK: "
                                + resultSet.getString(
                                "medicine_name")
                                + " | Company: "
                                + resultSet.getString(
                                "company")
                                + " | Quantity Left: "
                                + resultSet.getInt(
                                "quantity")
                );
            }

            if (!found) {

                System.out.println(
                        "✅ No low stock medicines!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Failed to check low stock alerts!"
            );

            e.printStackTrace();
        }
    }
}