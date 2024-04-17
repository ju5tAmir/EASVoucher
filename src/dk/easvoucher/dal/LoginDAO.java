package dk.easvoucher.dal;

import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.db.DBConnection;

import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAO {

    private final DBConnection databaseConnection;

    /**
     * Constructs a new LoginDAO instance with a new database connection.
     *
     */
    public LoginDAO() {
        databaseConnection = new DBConnection();
    }

    /**
     * Authenticate the provided username and password from database.
     * If authentication is successful, returns an Employee object representing the authenticated user.
     * Otherwise, throws an ExceptionHandler.
     *
     * @param username The username to be authenticated.
     * @param password The password associated with the username.
     * @return Employee object related to authenticated user.
     * @throws ExceptionHandler throws an error if password was incorrect or username was not found in database.
     */
    public Employee loginAuth(String username, String password) throws ExceptionHandler {
        // SQL query to retrieve employee details based on username and password
        String query = "SELECT * FROM Employees WHERE username = ? AND password = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            // Set username and password parameters
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if we got any answer from sql server
            if (resultSet.next()){
                // Authentication successful, creates and return Employee object
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setUsername(resultSet.getString("username"));
                // Set UserRole value by retrieved role from database
                employee.setRole(UserRole.fromString(resultSet.getString("role")));
                return employee;
            }
            else{
                // Authentication failed, throw exception
                throw new ExceptionHandler(ExceptionMessage.INCORRECT_CREDENTIALS.getValue());
            }

        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                    + "\n"
                    + ex.getMessage());
        }

    }


}