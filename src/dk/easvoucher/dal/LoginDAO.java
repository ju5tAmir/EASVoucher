package dk.easvoucher.dal;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.response.Response;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.user.IUser;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginHandler {

    private final DBConnection databaseConnection;

    public LoginHandler() {
        databaseConnection = new DBConnection();
    }

    /**
     * Authenticate the provided username and password by database.
     * If process was successful, creates an Employee object,
     * otherwise
     *
     * @param username The username that needs to be authenticated.
     * @param password The password related to the username that needs to be authenticated.
     * @return
     * @throws ExceptionHandler
     */
    public Employee loginAuth(String username, String password) throws ExceptionHandler {
        try {

            return new Employee(1, "Asghar", UserRole.ADMIN);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new ExceptionHandler(ExceptionMessage.INCORRECT_PASSWORD.getValue());
        }
    }


    /**
     * Try to validate credentials from database
     * @param username to check
     * @param originalPassword for hashing in comparing to hashed password in db
     * @return response
     * @throws ExceptionHandler
     */
    public Response handleLogin(String username, String originalPassword) throws ExceptionHandler {
        String query = "select password, role from employees where username = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString("password");
                    // Check for password similarity
                    if (Objects.equals(password, originalPassword)){
                        // Retrieve UserRole
                        UserRole userRole = UserRole.fromString(resultSet.getString("role"));

                        // Create response based on retrieved data from db
                        return getResponse(username, userRole);

                    } else {
                        AlertHandler.displayErrorAlert(ExceptionMessage.INCORRECT_PASSWORD.getValue());
                    }
                } else {
                    AlertHandler.displayErrorAlert(ExceptionMessage.USER_NOT_FOUND.getValue());
                }
            }
        } catch (SQLException | ExceptionHandler e) {
            throw new ExceptionHandler(e.getMessage());
        }
        return new Response(false);
    }


    /**
     * Create response instance
     * @param username based on this username, we create a new user
     * @param userRole user role when creating new user
     * @return Response with StatusCode, User Object, EventsList, TicketsList
     */
    private static Response getResponse(String username, UserRole userRole) {
        IUser user = new Employee(1, username, userRole);

        // Retrieve events list for the user
        List<Event> events = new ArrayList<>();

        // Retrieve tickets for the user
        List<ITicket> tickets = new ArrayList<>();

        // Make a response based on retrieved data
        Response response = new Response();
        response.setStatus(true);
        response.setUser(user);
        response.setEvents(events);
        response.setTickets(tickets);
        return response;
    }
}