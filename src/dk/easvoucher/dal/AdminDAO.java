package dk.easvoucher.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easvoucher.be.entities.Event;
import dk.easvoucher.be.entities.User;
import dk.easvoucher.dal.db.DataConnection;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private DataConnection dbConnection;

    public AdminDAO() {
        dbConnection = new DataConnection();
    }

    public void createUser(String username, String hashedPassword, Integer roleId) {
        String sql = "INSERT INTO Users (Username, HashedPassword, RoleId) VALUES (?, ?, ?);";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setObject(3, roleId);
            pstmt.executeUpdate();
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT UserId, Username, HashedPassword, RoleId FROM Users;";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserId"),
                        rs.getString("Username"),
                        rs.getString("HashedPassword"),
                        (Integer) rs.getObject("RoleId")
                );
                users.add(user);
            }
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void updateUser(int userId, String username, String hashedPassword, Integer roleId) {
        String sql = "UPDATE Users SET Username = ?, HashedPassword = ?, RoleId = ? WHERE UserId = ?;";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.setObject(3, roleId);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE UserId = ?;";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("EventId"),
                        rs.getString("EventName"),
                        rs.getString("Description"),
                        rs.getString("StartTime"),
                        rs.getString("EndTime"),
                        rs.getString("Location"),
                        rs.getInt("CoordinatorId")));
            }
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    public void deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE EventId = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();
        } catch (SQLServerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public boolean assignCoordinator(int eventId, int coordinatorId, int assignedBy) {
        String sql = "INSERT INTO DetailedAccessManagement (EventId, CoordinatorId, AssignedBy) VALUES (?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.setInt(2, coordinatorId);
            pstmt.setInt(3, assignedBy);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
}
