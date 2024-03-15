package dk.easvoucher.dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDAO {

    private DatabaseConnection databaseConnection;

    public AdminDAO() {
        this.databaseConnection = new DatabaseConnection();
    }

    public void createUser(String username, String hashedPassword, String roleName) {
        String sql = "INSERT INTO Users (Username, HashedPassword, RoleId) " +
                "VALUES (?, ?, (SELECT RoleId FROM UserRoles WHERE RoleName = ?))";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, roleName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignCoordinator(int userId, int eventId) {
        String sql = "UPDATE Events SET CoordinatorId = ? WHERE EventId = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE EventId = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateUser(int userId, String newUsername, String newHashedPassword, String newRoleName) {
        String sql = "UPDATE Users SET Username = ?, HashedPassword = ?, RoleId = (SELECT RoleId FROM UserRoles WHERE RoleName = ?) WHERE UserId = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, newHashedPassword);
            pstmt.setString(3, newRoleName);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

