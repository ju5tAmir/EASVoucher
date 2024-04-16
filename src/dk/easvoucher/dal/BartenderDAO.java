package dk.easvoucher.dal;

import dk.easvoucher.be.ticket.Item;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.ticket.TicketType;
import dk.easvoucher.be.user.Customer;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BartenderDAO {

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
    public Ticket getTicketByUUID(String uuid) throws ExceptionHandler{
        // Ticket object to return
        Ticket ticket = new Ticket();

        // SQL query to select all tickets by provided event id
        String query = "SELECT * " +
                "FROM Tickets " +
                "WHERE Tickets.uuid = ?;";

        // Try to establish sql connection and execute the query
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            // Set event ID parameter to statement
            preparedStatement.setString(1, uuid);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate over result set
            if (resultSet.next()){
                Customer customer = new Customer();

                // Update ticket object
                ticket.setId(resultSet.getInt("id"));
                ticket.setTicketType(TicketType.STANDARD);
                ticket.setUUID(UUID.fromString(resultSet.getString("uuid")));
                customer.setId(resultSet.getInt("customer_id"));
                ticket.setCustomer(customer);

//                // Update ticket with items
                ticket.setItems(getTicketItems(ticket));

                // TODO: needs better comments
                getCustomer(ticket);

            }
            // Return list of tickets
            return ticket;

        } catch (SQLException | ExceptionHandler ex) {
            // Throw an exception if there is any error with the database connection
            throw new ExceptionHandler(
                    // Database failure message
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue() +
                            // New line
                            "\n" +
                            // Failure message details
                            ex.getMessage());
        }
    }



    public void getCustomer(Ticket ticket) throws ExceptionHandler{
        // Customer object for the ticket
        Customer customer = ticket.getCustomer();

        // SQL query to select all tickets by provided event id
        String query = "SELECT * " +
                "FROM Customers " +
                "WHERE id = ?;";

        // Try to establish sql connection and execute the query
        try (//Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            // Set event ID parameter to statement
            preparedStatement.setInt(1, customer.getId());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();



            // Update customer object
            if (resultSet.next()) {
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
            }
        } catch (SQLException ex) {
            // Throw an exception if there is any error with the database connection
            throw new ExceptionHandler(
                    // Database failure message
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue() +
                            // New line
                            "\n" +
                            // Failure message details
                            ex.getMessage());
        }
    }

    public List<Item> getTicketItems(Ticket ticket) throws ExceptionHandler{
        // List to store all items
        List<Item> items = new ArrayList<>();
        // SQL query to select all items by provided id
        String query = "SELECT * " +
                "FROM TicketItems " +
                "WHERE ticket_id = ?;";

        // Try to establish sql connection and execute the query
        try (//Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            // Set ticket ID parameter to statement
            preparedStatement.setInt(1, ticket.getId());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate over result set
            while (resultSet.next()){
                // Create item object to update with database data
                Item item = new Item();

                // Update item object
                item.setId(resultSet.getInt("id"));
                item.setTitle(resultSet.getString("item"));
                item.setClaimed(resultSet.getBoolean("is_claimed"));

                // Add item into items list
                items.add(item);
            }

            // Return list of items
            return items;

        } catch (SQLException ex) {
            // Throw an exception if there is any error with the database connection
            throw new ExceptionHandler(
                    // Database failure message
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue() +
                            // New line
                            "\n" +
                            // Failure message details
                            ex.getMessage());
        }



    }

}
