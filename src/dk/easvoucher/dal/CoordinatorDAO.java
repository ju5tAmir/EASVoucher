package dk.easvoucher.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easvoucher.be.Event;
import dk.easvoucher.be.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        }

        return events;
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

    public void insertTicket(String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        String sql = "INSERT INTO Tickets (TicketNumber, QrCode, Barcode, UUID, TicketTypeId, EventId, CustomerId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketNumber);
            pstmt.setString(2, qrCode);
            pstmt.setString(3, barcode);
            pstmt.setString(4, UUID);
            pstmt.setObject(5, ticketTypeId);
            pstmt.setInt(6, eventId);
            pstmt.setObject(7, customerId);
            pstmt.executeUpdate();
        }
    }

    public List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("TicketId"),
                        rs.getString("TicketNumber"),
                        rs.getString("QrCode"),
                        rs.getString("Barcode"),
                        rs.getString("UUID"),
                        (Integer) rs.getObject("TicketTypeId"), // Use getObject to handle possible NULL values
                        rs.getInt("EventId"),
                        (Integer) rs.getObject("CustomerId"));
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public void updateTicket(int ticketId, String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        String sql = "UPDATE Tickets SET TicketNumber = ?, QrCode = ?, Barcode = ?, UUID = ?, TicketTypeId = ?, EventId = ?, CustomerId = ? WHERE TicketId = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ticketNumber);
            pstmt.setString(2, qrCode);
            pstmt.setString(3, barcode);
            pstmt.setString(4, UUID);
            pstmt.setObject(5, ticketTypeId);
            pstmt.setInt(6, eventId);
            pstmt.setObject(7, customerId);
            pstmt.setInt(8, ticketId);
            pstmt.executeUpdate();
        }
    }

    public void deleteTicket(int ticketId) throws SQLException {
        String sql = "DELETE FROM Tickets WHERE TicketId = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();
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
        }
    }
}

