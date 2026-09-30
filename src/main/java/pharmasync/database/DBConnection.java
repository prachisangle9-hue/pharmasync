package pharmasync.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Database Connection Method
    public static Connection getConnection() {

        try {

            String url = "jdbc:mysql://localhost:3306/pharmasync";

            String username = "root";

            String password = "Prachi@123";

            Connection con = DriverManager.getConnection(url, username, password);

            System.out.println("Database Connected Successfully!");

            return con;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
    public static void main(String[] args) {

        Connection con = getConnection();

        if (con != null) {

            System.out.println("Connection Successful!");

        } else {

            System.out.println("Connection Failed!");

        }
    }
}
