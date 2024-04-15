package dk.easvoucher.dal;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

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
    public List<Employee> getAllEmployees() throws ExceptionHandler{
        // List to store all employees
        List<Employee> employees = new ArrayList<>();

        String query = "SELECT *" +
                " FROM Employees";

        try (//Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if we got any answer from sql server
            while (resultSet.next()){
                // Employee object to add into final list
                Employee employee = new Employee();
                // Get first_name from results set
                employee.setId(resultSet.getInt("id"));
                employee.setUsername(resultSet.getString("username"));
                employee.setRole(UserRole.fromString(resultSet.getString("role")));

                // Add the retrieved name to names list
                employees.add(employee);
            }
            return employees;

        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        }
    }

    public Employee createEmployee(String username, String password, UserRole role) throws ExceptionHandler {
        String query = "INSERT INTO Employees(username, password, role) VALUES(?,?,?)";

        try (
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role.getValue());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExceptionHandler(ExceptionMessage.ERROR_IN_CREATING_USER.getValue());
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Employee employee = new Employee();
                    employee.setId(generatedKeys.getInt(1)); // Assuming ID is the first generated key
                    employee.setUsername(username);
                    employee.setRole(role);
                    return employee;
                } else {
                    throw new ExceptionHandler(ExceptionMessage.ERROR_IN_CREATING_USER.getValue());
                }
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ExceptionMessage.DB_CONNECTION_FAILURE.getValue() + "\n" + ex.getMessage());
        }
    }

    public void removeEmployee(Employee employee) throws ExceptionHandler {
        String sql = "DELETE FROM Employees WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, employee.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExceptionHandler(ExceptionMessage.ERROR_IN_REMOVING_USER.getValue());
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ExceptionMessage.DB_CONNECTION_FAILURE.getValue() + "\n" + ex.getMessage());
        }
    }

    public void updateEmployee(Employee employee) throws ExceptionHandler {
        String query = "UPDATE Employees SET username = ?, password = ?, role = ? WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, employee.getUsername());
            statement.setString(2, employee.getPassword());
            statement.setString(3, employee.getRole().getValue());
            statement.setInt(4, employee.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExceptionHandler(ExceptionMessage.ERROR_IN_UPDATING_USER.getValue());
            }
        } catch (SQLException ex) {
            throw new ExceptionHandler(ExceptionMessage.DB_CONNECTION_FAILURE.getValue() + "\n" + ex.getMessage());
        }
    }


    public List<Event> getAllEvents() throws ExceptionHandler {
        // List to store all Events
        List<Event> events = new ArrayList<>();

        String query = "SELECT *" +
                " FROM Events";

        try (PreparedStatement statement = conn.prepareStatement(query))
        {
            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if we got any answer from sql server
            while (resultSet.next()){
                // Event object to add into final list
                Event event = new Event();
                // Get first_name from results set
                event.setId(resultSet.getInt("id"));
                event.setName(resultSet.getString("title"));
                event.setLocation(resultSet.getString("location"));
                event.setStartTime(resultSet.getTime("start_time"));

                // Add the retrieved name to names list
                events.add(event);
            }
            return events;

        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        }
    }

    public void removeEvent(Event event) throws ExceptionHandler {
        String[] deleteQueries = {
                "DELETE FROM EventNotes WHERE event_id = ?",
                "DELETE FROM EventCoordinators WHERE event_id = ?",
                "DELETE FROM EventTickets WHERE event_id = ?",
                "DELETE FROM SuperTicket WHERE event_id = ?",
                "DELETE FROM TicketItems WHERE ticket_id IN (SELECT id FROM Tickets WHERE event_id = ?)",
                "DELETE FROM Tickets WHERE event_id = ?",
                "DELETE FROM Events WHERE id = ?"
        };

        try {
            conn.setAutoCommit(false);

            // Execute each delete query with batch
            for (String query : deleteQueries) {
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    // Set event ID parameter
                    preparedStatement.setInt(1, event.getId());
                    // Add query to the batch
                    preparedStatement.addBatch();
                    // Execute the batch
                    preparedStatement.executeBatch();
                }
            }

            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                // Log or handle the rollback exception
                System.out.println(rollbackEx.getMessage());
            }

            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue() +
                            "\n" +
                            ex.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException setAutoCommitEx) {
                // Log or handle the exception
                System.out.println(setAutoCommitEx.getMessage());
            }
        }
    }
}
