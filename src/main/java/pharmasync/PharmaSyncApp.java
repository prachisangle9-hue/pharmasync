package pharmasync;

import java.util.Scanner;

public class PharmaSyncApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        RoleManager roleManager = new RoleManager();

        System.out.println();
        System.out.println("========================================");
        System.out.println("          WELCOME TO PHARMASYNC");
        System.out.println("========================================");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        String role = userDAO.loginAndGetRole(
                username,
                password
        );


        if (role == null) {

            System.out.println(
                    "❌ Login failed! Access denied."
            );

            scanner.close();
            return;
        }

        AuditLogDAO auditLogDAO = new AuditLogDAO();

        auditLogDAO.addLog(
                username,
                "LOGIN",
                "User logged into system"
        );

        System.out.println();
        System.out.println("✅ Login successful!");
        System.out.println("👤 Role: " + role);

        if (roleManager.isAdmin(role)) {

            showAdminMenu(scanner);

        } else if (roleManager.isPharmacist(role)) {

            showPharmacistMenu(scanner);
        }

        scanner.close();
    }


    // ================================
    // ADMIN MENU
    // ================================

    public static void showAdminMenu(
            Scanner scanner) {

        MedicineDAO medicineDAO =
                new MedicineDAO();

        DashboardDAO dashboardDAO =
                new DashboardDAO();

        SaleDAO saleDAO =
                new SaleDAO();

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "             ADMIN MENU"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "1. Add Medicine"
            );

            System.out.println(
                    "2. View All Medicines"
            );

            System.out.println(
                    "3. Search Medicine"
            );

            System.out.println(
                    "4. Update Medicine"
            );

            System.out.println(
                    "5. Delete Medicine"
            );

            System.out.println(
                    "6. Dashboard"
            );

            System.out.println(
                    "7. Sell Medicine"
            );

            System.out.println(
                    "8. Sales History"
            );

            System.out.println(
                    "9. Logout"
            );

            System.out.println(
                    "========================================"
            );

            System.out.print(
                    "Enter your choice: "
            );

            int choice =
                    scanner.nextInt();

            scanner.nextLine();


            switch (choice) {


                // 1. ADD MEDICINE
                case 1:

                    System.out.print(
                            "Enter Medicine Name: "
                    );

                    String medicineName =
                            scanner.nextLine();

                    System.out.print(
                            "Enter Company: "
                    );

                    String company =
                            scanner.nextLine();

                    System.out.print(
                            "Enter Category: "
                    );

                    String category =
                            scanner.nextLine();

                    System.out.print(
                            "Enter Batch No: "
                    );

                    String batchNo =
                            scanner.nextLine();

                    System.out.print(
                            "Enter Manufacture Date (YYYY-MM-DD): "
                    );

                    String manufactureDate =
                            scanner.nextLine();

                    System.out.print(
                            "Enter Expiry Date (YYYY-MM-DD): "
                    );

                    String expiryDate =
                            scanner.nextLine();

                    System.out.print(
                            "Enter Price: "
                    );

                    double price =
                            scanner.nextDouble();

                    System.out.print(
                            "Enter Quantity: "
                    );

                    int quantity =
                            scanner.nextInt();

                    scanner.nextLine();

                    System.out.print(
                            "Enter Barcode: "
                    );

                    String barcode =
                            scanner.nextLine();

                    medicineDAO.addMedicine(
                            medicineName,
                            company,
                            category,
                            batchNo,
                            manufactureDate,
                            expiryDate,
                            price,
                            quantity,
                            barcode
                    );

                    break;


                // 2. VIEW ALL MEDICINES
                case 2:

                    medicineDAO.viewAllMedicines();

                    break;


                // 3. SEARCH MEDICINE
                case 3:

                    System.out.print(
                            "Enter medicine name: "
                    );

                    String searchName =
                            scanner.nextLine().trim();

                    medicineDAO.searchMedicine(
                            searchName
                    );

                    break;


                // 4. UPDATE MEDICINE
                case 4:

                    System.out.print(
                            "Enter Medicine ID: "
                    );

                    int updateId =
                            scanner.nextInt();

                    System.out.print(
                            "Enter New Price: "
                    );

                    double newPrice =
                            scanner.nextDouble();

                    System.out.print(
                            "Enter New Quantity: "
                    );

                    int newQuantity =
                            scanner.nextInt();

                    scanner.nextLine();

                    medicineDAO.updateMedicine(
                            updateId,
                            newPrice,
                            newQuantity
                    );

                    break;


                // 5. DELETE MEDICINE
                case 5:

                    System.out.print("Enter Medicine ID: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Are you sure? (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {

                        medicineDAO.deleteMedicine(deleteId);

                    } else {

                        System.out.println("❌ Delete cancelled.");

                    }

                    break;


                // 6. DASHBOARD
                case 6:

                    dashboardDAO.showDashboard();

                    break;


                // 7. SELL MEDICINE
                case 7:

                    System.out.print(
                            "Enter Medicine ID: "
                    );

                    int medicineId =
                            scanner.nextInt();

                    System.out.print(
                            "Enter Quantity Sold: "
                    );

                    int quantitySold =
                            scanner.nextInt();

                    scanner.nextLine();

                    saleDAO.sellMedicine(
                            medicineId,
                            quantitySold
                    );

                    break;


                // 8. SALES HISTORY
                case 8:

                    saleDAO.viewSalesHistory();

                    break;


                // 9. LOGOUT
                case 9:

                    System.out.println(
                            "👋 Logged out successfully!"
                    );

                    return;


                default:

                    System.out.println(
                            "❌ Invalid choice!"
                    );
            }
        }
    }


    // ================================
    // PHARMACIST MENU
    // ================================

    public static void showPharmacistMenu(
            Scanner scanner) {

        MedicineDAO medicineDAO =
                new MedicineDAO();

        SaleDAO saleDAO =
                new SaleDAO();

        while (true) {

            System.out.println();
            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "          PHARMACIST MENU"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "1. View All Medicines"
            );

            System.out.println(
                    "2. Search Medicine"
            );

            System.out.println(
                    "3. Sell Medicine"
            );

            System.out.println(
                    "4. Sales History"
            );

            System.out.println(
                    "5. Logout"
            );

            System.out.println(
                    "========================================"
            );

            System.out.print(
                    "Enter your choice: "
            );

            int choice =
                    scanner.nextInt();

            scanner.nextLine();


            switch (choice) {


                // 1. VIEW ALL MEDICINES
                case 1:

                    medicineDAO.viewAllMedicines();

                    break;


                // 2. SEARCH MEDICINE
                case 2:

                    System.out.print(
                            "Enter medicine name: "
                    );

                    String medicineName =
                            scanner.nextLine().trim();

                    medicineDAO.searchMedicine(
                            medicineName
                    );

                    break;


                // 3. SELL MEDICINE
                case 3:

                    System.out.print(
                            "Enter Medicine ID: "
                    );

                    int medicineId =
                            scanner.nextInt();

                    System.out.print(
                            "Enter Quantity Sold: "
                    );

                    int quantitySold =
                            scanner.nextInt();

                    scanner.nextLine();

                    saleDAO.sellMedicine(
                            medicineId,
                            quantitySold
                    );

                    break;


                // 4. SALES HISTORY
                case 4:

                    saleDAO.viewSalesHistory();

                    break;


                // 5. LOGOUT
                case 5:

                    System.out.println(
                            "👋 Logged out successfully!"
                    );

                    return;


                default:

                    System.out.println(
                            "❌ Invalid choice!"
                    );
            }
        }
    }
}