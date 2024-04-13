package dk.easvoucher.dal;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private final DBConnection dbConnection;
    public EventDAO(){
        this.dbConnection= new DBConnection();
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
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("event_id");
                String name = resultSet.getString("name");
                LocalDateTime time = resultSet.getTimestamp("time").toLocalDateTime();
                String location = resultSet.getString("location");
                String notes = resultSet.getString("notes");
                int coordinatorId = resultSet.getInt("coordinator_id");

                Event event = new Event(id, name, time, location, notes, coordinatorId);
                events.add(event);
            }
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
        return events;

    }
}
