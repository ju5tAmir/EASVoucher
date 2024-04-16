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

public class UpdateEventDAO {
    private final DBConnection databaseConnection = new DBConnection();
    private final Connection conn;

    // TODO: Implement a way to get ID of notes in batch, it wasn't working properly, so I just insert without getting generated IDs from DB

    // FIX IT
    {
        try {
            conn = databaseConnection.getConnection();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEvent(Event event) throws ExceptionHandler{
        // Query to update event table changes
        String eventQueryUpdate = "UPDATE Events SET title=?, location=?, start_date=?, start_time=?, end_date=?, end_time=? WHERE id=?";

        try {
            // Turn on rollbacks
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try (PreparedStatement eventStmt = conn.prepareStatement(eventQueryUpdate)){
                eventStmt.setString(1, event.getName());
                eventStmt.setString(2, event.getLocation());
                eventStmt.setDate(3, (java.sql.Date) event.getStartDate());
                eventStmt.setTime(4, event.getStartTime());
                eventStmt.setDate(5, (java.sql.Date) event.getEndDate());
                eventStmt.setTime(6, event.getEndTime());
                eventStmt.setInt(7, event.getId());

                int rows = eventStmt.executeUpdate();

                if (rows == 0) {
                    throw new ExceptionHandler(ExceptionMessage.EVENT_UPDATE_ERROR.getValue());
                }

                // Handle notes
                handleNotes(event);

                // Handle coordinators
                 handleCoordinators(event);

            }

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new ExceptionHandler(ExceptionMessage.DB_CONNECTION_FAILURE.getValue() + "\n" + e.getMessage());
        }


    }


    private void handleNotes(Event event) throws ExceptionHandler{
        // Query to update a note when it exists in database and changed in the program
        String updateQuery = "UPDATE EventNotes SET note=? WHERE id=?";
        // Query to delete a note when it's deleted in program and exists in database
        String deleteQuery = "DELETE FROM EventNotes WHERE id=?";
        // Query to create a note when it doesn't exist in database, but exists in the program object
        String insertQuery = "INSERT INTO EventNotes(note, event_id) VALUES(?,?)";

        // Retrieved notes from database related to the event
        List<Note> allNotesFromDB = getAllNotes(event);

        // If nothing has changed in notes
        if (allNotesFromDB.equals(event.getNotes())){
            return;
        }

        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)){



            // Iterate over notes from database to deletion
            for (Note note: allNotesFromDB){

                // Deleted note, exists in database but not in program
                if (!event.getNotes().contains(note)){
                    deleteStmt.setInt(1, note.getId());
                    deleteStmt.addBatch();
                }

            }

            // Iterate over event notes of object
            for (Note note: event.getNotes()) {

                // New note, exist in program but not in database
                if (!allNotesFromDB.contains(note)) {
                    // Insert newly created note to the database
                    insertStmt.setString(1, note.getNote());
                    insertStmt.setInt(2, event.getId());
                    insertStmt.addBatch();

                } else {
                    // Update if lists are not equals
                    updateStmt.setString(1, note.getNote());
                    updateStmt.setInt(2, note.getId());
                    updateStmt.addBatch();

                }
            }


            insertStmt.executeBatch();
            updateStmt.executeBatch();
            deleteStmt.executeBatch();

        } catch (SQLException e) {
            throw new ExceptionHandler(e.getMessage());
        }

    }

    private List<Note> getAllNotes(Event event) throws ExceptionHandler{
        // List to store all notes to return
        List<Note> notes = new ArrayList<>();

        // Query to get all notes for the event
        String sql = "SELECT * FROM EventNotes WHERE event_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, event.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                // Note object
                Note note = new Note();
                note.setId(rs.getInt("id"));
                note.setNote(rs.getString("note"));
                notes.add(note);
            }
            return notes;

        } catch (SQLException e){
            throw new ExceptionHandler(ExceptionMessage.DB_CONNECTION_FAILURE.getValue());
        }

    }


    private void handleCoordinators(Event event) throws  ExceptionHandler{
        // Query to assign a new coordinator to the event
        String insertQuery = "INSERT INTO EventCoordinators(event_id, coordinator_id) VALUES(?,?)";
        // Query to delete the unassigned coordinator
        String deleteQuery = "DELETE FROM EventCoordinators WHERE event_id=?";

        // List of coordinators retrieved from database
        List<Employee> allCoordinatorsFromDB = getAllCoordinators(event);

        if (allCoordinatorsFromDB.equals(event.getCoordinators())){
            return;
        }

        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            // Iterate over coordinators from database to deletion
            for (Employee coordinator : allCoordinatorsFromDB) {
                // Deleted coordinator, exists in database but not in program
                if (!event.getCoordinators().contains(coordinator)) {
                    deleteStmt.setInt(1, coordinator.getId());
                    deleteStmt.addBatch();
                }
            }

            // Iterate over existing event coordinators of program
            for (Employee coordinator : event.getCoordinators()) {
                // New coordinator, exist in program but not in the database
                if (!allCoordinatorsFromDB.contains(coordinator)) {
                    // Insert newly created note to the database
                    insertStmt.setInt(1, event.getId());
                    insertStmt.setInt(2, coordinator.getId());
                    insertStmt.addBatch();
                }

                // Execute insert and delete batches
                insertStmt.executeBatch();
                deleteStmt.executeBatch();
            }
        } catch (SQLException e) {
            throw new ExceptionHandler(e.getMessage());
        }

    }


    private List<Employee> getAllCoordinators(Event event) throws  ExceptionHandler{
        List<Employee> coordinators = new ArrayList<>();

        // Query to get all employees related to the event
        String sql = "SELECT * FROM Employees JOIN dbo.EventCoordinators EC on Employees.id = EC.coordinator_id WHERE event_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setInt(1, event.getId());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Employee coordinator = new Employee();
                coordinator.setId(rs.getInt("id"));
                coordinator.setUsername(rs.getString("username"));
                coordinator.setRole(UserRole.fromString(rs.getString("role")));

                coordinators.add(coordinator);
            }

            return coordinators;

        } catch (SQLException e){
            throw new ExceptionHandler(e.getMessage());
        }
    }


}
