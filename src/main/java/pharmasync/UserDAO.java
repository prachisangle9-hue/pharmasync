package pharmasync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // User Login
    public void login(String username, String password) {

        String sql =
                "SELECT user_id, full_name, role " +
                        "FROM users " +
                        "WHERE username = ? AND password = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                int userId =
                        resultSet.getInt("user_id");

                String fullName =
                        resultSet.getString("full_name");

                String role =
                        resultSet.getString("role");

                System.out.println();
                System.out.println(
                        "========================================"
                );

                System.out.println(
                        "          LOGIN SUCCESSFUL"
                );

                System.out.println(
                        "========================================"
                );

                System.out.println(
                        "User ID   : " + userId
                );

                System.out.println(
                        "Name      : " + fullName
                );

                System.out.println(
                        "Role      : " + role
                );

                System.out.println(
                        "========================================"
                );

            } else {

                System.out.println(
                        "❌ Invalid username or password!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Login failed!"
            );

            e.printStackTrace();
        }
    }
    // Login and return user role
    public String loginAndGetRole(
            String username,
            String password) {

        String sql =
                "SELECT role FROM users " +
                        "WHERE username = ? AND password = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getString("role");
            }

        } catch (SQLException e) {

            System.out.println(
                    "❌ Login failed!"
            );

            e.printStackTrace();
        }

        return null;
    }
}
