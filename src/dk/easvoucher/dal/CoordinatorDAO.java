package dk.easvoucher.dal;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoordinatorDAO {

    private final DBConnection dbConnection;

    public CoordinatorDAO() {
        this.dbConnection = new DBConnection();
    }

    // Create an Event
    public void createEvent(Integer event_id, String name, String time, String location, String notes, Integer coordinatorId) throws SQLException {
        String sql = "INSERT INTO events (event_id, name, time, location, notes, coordinator_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, event_id);
            statement.setString(2, name);
            statement.setString(3, time);
            statement.setString(4, location);
            statement.setString(5, notes);
            statement.setInt(6, coordinatorId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> readAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("event_id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("time").toLocalDateTime(),
                        resultSet.getString("location"),
                        resultSet.getString("notes"),
                        resultSet.getInt("coordinator_id")
                );
                events.add(event);
            }
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
        return events;
    }


    // Update an Event
    public void updateEvent(Integer eventId, String name, String time, String location, String notes, Integer coordinatorId) throws SQLException {
        String sql = "UPDATE events SET name = ?, time = ?, location = ?, notes = ?, coordinator_id = ? WHERE event_id = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, time);
            statement.setString(3, location);
            statement.setString(4, notes);
            statement.setInt(5, coordinatorId);
            statement.setInt(6, eventId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    // Delete an Event
    public void deleteEvent(Integer eventId) throws SQLException {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    // Create a Ticket
    public void createTicket(Integer ticket_id, String qrCode, String barcode, Integer typeId, Integer eventId, Integer customer_id) throws SQLException {
        String sql = "INSERT INTO tickets (ticket_id, qr_code, barcode, type_id, event_id, customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, ticket_id);
            statement.setString(2, qrCode);
            statement.setString(3, barcode);
            statement.setInt(4, typeId);
            statement.setInt(5, eventId);
            statement.setInt(6, customer_id);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    // Read all Tickets
    public List<Ticket> readAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getInt("ticket_id"),
                        resultSet.getString("qr_code"),
                        resultSet.getString("barcode"),
                        resultSet.getInt("type_id"),
                        resultSet.getInt("event_id"),
                        resultSet.getInt("customer_id")
                );
                tickets.add(ticket);
            }
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    // Update a Ticket
    public void updateTicket(Integer ticketId, String qrCode, String barcode, Integer typeId, Integer eventId, Integer customerId) throws SQLException {
        String sql = "UPDATE tickets SET qr_code = ?, barcode = ?, type_id = ?, event_id = ?, customer_id = ? WHERE ticket_id = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, qrCode);
            statement.setString(2, barcode);
            statement.setInt(3, typeId);
            statement.setInt(4, eventId);
            statement.setInt(5, customerId);
            statement.setInt(6, ticketId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    // Delete a Ticket
    public void deleteTicket(Integer ticketId) throws SQLException {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, ticketId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public void assignCoordinatorToEvent(String eventName, int coordinatorId) throws SQLException {
        String sql = "UPDATE events SET coordinator_id = ? WHERE name = ?";
        try (Connection conn = dbConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, coordinatorId);
            statement.setString(2, eventName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully assigned coordinator to event(s).");
            } else {
                System.out.println("No event found with the specified name.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating the event with a new coordinator: " + e.getMessage());
            throw e;
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
}