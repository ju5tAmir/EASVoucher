package dk.easvoucher.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CoordinatorDAO {

    private final DatabaseConnection dbConnection;

    public CoordinatorDAO() {
        dbConnection = new DatabaseConnection();
    }

    public boolean createEvent(String eventName, String description, Timestamp startTime, Timestamp endTime, String location, int coordinatorId) {
        String sql = "INSERT INTO Events (EventName, Description, StartTime, EndTime, Location, CoordinatorId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eventName);
            pstmt.setString(2, description);
            pstmt.setTimestamp(3, startTime);
            pstmt.setTimestamp(4, endTime);
            pstmt.setString(5, location);
            pstmt.setInt(6, coordinatorId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE EventId = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEvent(int eventId, String eventName, String description, Timestamp startTime, Timestamp endTime, String location) {
        String sql = "UPDATE Events SET EventName = ?, Description = ?, StartTime = ?, EndTime = ?, Location = ? WHERE EventId = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eventName);
            pstmt.setString(2, description);
            pstmt.setTimestamp(3, startTime);
            pstmt.setTimestamp(4, endTime);
            pstmt.setString(5, location);
            pstmt.setInt(6, eventId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addTicketToEvent(String ticketNumber, String qrCode, String barcode, String uuid, int ticketTypeId, int eventId, int customerId) {
        String sql = "INSERT INTO Tickets (TicketNumber, QrCode, Barcode, UUID, TicketTypeId, EventId, CustomerId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketNumber);
            pstmt.setString(2, qrCode);
            pstmt.setString(3, barcode);
            pstmt.setString(4, uuid);
            pstmt.setInt(5, ticketTypeId);
            pstmt.setInt(6, eventId);
            pstmt.setInt(7, customerId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

