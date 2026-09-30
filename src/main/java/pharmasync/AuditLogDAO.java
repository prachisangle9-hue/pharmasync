package pharmasync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditLogDAO {

    public void addLog(String username, String action, String details) {

        String sql = """
                INSERT INTO audit_logs
                (username, action, details)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, action);
            statement.setString(3, details);

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println("❌ Failed to save audit log!");
            e.printStackTrace();
        }
    }
}
