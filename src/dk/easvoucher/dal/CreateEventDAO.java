package dk.easvoucher.dal;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.event.Note;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreateEventDAO {

    private final DBConnection databaseConnection = new DBConnection();
    private final Connection conn;

    // FIX IT
    {
        try {
            conn = databaseConnection.getConnection();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
    public List<Employee> getAllCoordinators() throws ExceptionHandler {
        List<Employee> coordinators = new ArrayList<>();

        String sql = "SELECT * FROM Employees WHERE role = 'coordinator'";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            ResultSet rs = preparedStatement.executeQuery();

            // Iterate over results
            while (rs.next()){
                // Employee (Coordinator) object to update
                Employee coordinator = new Employee();

                coordinator.setId(rs.getInt("id"));
                coordinator.setUsername(rs.getString("username"));
                coordinator.setRole(UserRole.fromString(rs.getString("role")));

                // add coordinator object to list of coordinators
                coordinators.add(coordinator);

            }
            // return the list
            return coordinators;


        } catch (SQLException ex){
            throw new ExceptionHandler(ExceptionMessage.DB_CONNECTION_FAILURE.getValue());
        }
    }

    public void createEvent(Event event) throws ExceptionHandler {
        // SQL query to create the event row in Events table
        String eventsQuery = "INSERT INTO Events(title, location, start_date, start_time, end_date, end_time, created_by) " +
                "VALUES(?,?,?,?,?,?,?)";
        // SQL query to create related note rows for the event
        String notesQuery = "INSERT INTO EventNotes(note, event_id) " +
                "VALUES(?,?)";
        // SQL query to assign coordinators to event
        String coordinatorsQuery = "INSERT INTO EventCoordinators(event_id, coordinator_id) " +
                "VALUES(?,?)";


        try {
            // Enable rollbacks
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            // Insert event
            try (PreparedStatement eventStmt = conn.prepareStatement(eventsQuery, Statement.RETURN_GENERATED_KEYS)) {
                eventStmt.setString(1, event.getName());
                eventStmt.setString(2, event.getLocation());
                eventStmt.setDate(3, (java.sql.Date) event.getStartDate());
                eventStmt.setTime(4, event.getStartTime());
                eventStmt.setDate(5, (java.sql.Date) event.getEndDate());
                eventStmt.setTime(6, event.getEndTime());
                eventStmt.setInt(7, event.getCreatedBy().getId());

                int rowsAffected = eventStmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new ExceptionHandler(ExceptionMessage.ERROR_IN_CREATING_EVENT.getValue());
                }
                // Retrieve the generated event ID
                try (ResultSet generatedKeys = eventStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setId(generatedKeys.getInt(1));
                        // Insert notes
                        try (PreparedStatement noteStmt = conn.prepareStatement(notesQuery)) {
                            for (Note note : event.getNotes()) {
                                // Add parameters for each note
                                noteStmt.setString(1, note.getNote());
                                noteStmt.setInt(2, event.getId());
                                // Add the current set of parameters to the batch
                                noteStmt.addBatch();
                            }
                            // Execute the batch update
                            noteStmt.executeBatch();
                        }

                        // Insert Coordinators

                        try (PreparedStatement coordStmt = conn.prepareStatement(coordinatorsQuery)) {
                            for (Employee coordinator : event.getCoordinators()) {
                                // Add parameters for each coordinator
                                coordStmt.setInt(1, event.getId());
                                coordStmt.setInt(2, coordinator.getId());
                                // Add the current set of parameters to the batch
                                coordStmt.addBatch();
                            }
                            // Execute the batch update
                            coordStmt.executeBatch();
                        }
                    }
                }
                // Commit transaction if no error occurred
                conn.commit();
            } catch (SQLException e){
                throw new ExceptionHandler(ExceptionMessage.INSERTION_FAILED.getValue());
            }

        } catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new ExceptionHandler(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.setTransactionIsolation(Connection.TRANSACTION_NONE);
            } catch (SQLException e){
                throw new ExceptionHandler(e.getMessage());
            }
        }

    }
}
