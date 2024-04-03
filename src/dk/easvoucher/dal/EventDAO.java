package dk.easvoucher.dal;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private final DBConnection dbConnection;
    public EventDAO(){
        this.dbConnection= new DBConnection();
    }
    public void createEvent(String title, String note, String location, LocalDate startDate, LocalDate endDate, int coordinatorId) throws SQLException {
        String sql = "INSERT INTO events (title, note, location, start_date, end_date, coordinator_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (   Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, note);
            statement.setString(3, location);
            statement.setDate(4, Date.valueOf(startDate));
            statement.setDate(5, Date.valueOf(endDate));
            statement.setInt(6, coordinatorId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
    public void updateEvent(int eventId, String title, String note, String location, LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "UPDATE events SET title = ?, note = ?, location = ?, start_date = ?, end_date = ? WHERE event_id = ?";
        try (   Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, note);
            statement.setString(3, location);
            statement.setDate(4, Date.valueOf(startDate));
            statement.setDate(5, Date.valueOf(endDate));
            statement.setInt(6, eventId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEvent(int eventId) throws SQLException {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            statement.executeUpdate();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("event_id"),
                        resultSet.getString("title"),
                        resultSet.getString("note"),
                        resultSet.getString("location"),
                        resultSet.getDate("start_date").toLocalDate(),
                        resultSet.getDate("end_date").toLocalDate()
                );
                events.add(event);
            }
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
        return events;
    }
}
