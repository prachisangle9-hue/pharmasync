package pharmasync;

public class RoleManager {

    // Check Admin Permission
    public boolean isAdmin(String role) {

        return role.equalsIgnoreCase("Admin");
    }


    // Check Pharmacist Permission
    public boolean isPharmacist(String role) {

        return role.equalsIgnoreCase("Pharmacist");
    }


    // Display Permissions
    public void showPermissions(String role) {

        System.out.println();
        System.out.println(
                "========================================"
        );

        System.out.println(
                "          USER PERMISSIONS"
        );

        System.out.println(
                "========================================"
        );

        if (isAdmin(role)) {

            System.out.println(
                    "Role: ADMIN"
            );

            System.out.println(
                    "✅ Add Medicine"
            );

            System.out.println(
                    "✅ View Medicines"
            );

            System.out.println(
                    "✅ Search Medicine"
            );

            System.out.println(
                    "✅ Update Medicine"
            );

            System.out.println(
                    "✅ Delete Medicine"
            );

            System.out.println(
                    "✅ Dashboard"
            );

            System.out.println(
                    "✅ Sales"
            );

            System.out.println(
                    "✅ Billing"
            );

        } else if (isPharmacist(role)) {

            System.out.println(
                    "Role: PHARMACIST"
            );

            System.out.println(
                    "✅ View Medicines"
            );

            System.out.println(
                    "✅ Search Medicine"
            );

            System.out.println(
                    "✅ Sales"
            );

            System.out.println(
                    "✅ Billing"
            );

            System.out.println(
                    "❌ Delete Medicine"
            );

            System.out.println(
                    "❌ Dashboard"
            );

        } else {

            System.out.println(
                    "❌ Unknown role!"
            );
        }

        System.out.println(
                "========================================"
        );
    }
}