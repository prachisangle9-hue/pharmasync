package pharmasync;

public class TestRole {

    public static void main(String[] args) {

        RoleManager roleManager =
                new RoleManager();

        roleManager.showPermissions("Pharmacist");
    }
}