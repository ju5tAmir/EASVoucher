package dk.easvoucher.dal;

import dk.easvoucher.be.ticket.Item;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.user.Customer;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import dk.easvoucher.gui.dashboard.coordinator.ticket.create.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreateTicketDAO {

    private final Connection conn;

    public CreateTicketDAO() throws ExceptionHandler{
        DBConnection dbConnection = new DBConnection();
        conn = dbConnection.getConnection();
    }

    public List<String> getItemsByCategory(Category category) throws ExceptionHandler {

        // SQL query to retrieve all customers first_name's
        String query = "SELECT " +
                category.getValue() +
                " FROM Customers";

        try (//Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            // List to store all names in order to return
            List<String> allNames = new ArrayList<>();

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if we got any answer from sql server
            while (resultSet.next()){
                // Get first_name from results set
                String name = resultSet.getString(category.getValue());

                // Add the retrieved name to names list
                allNames.add(name);
            }
            return allNames;

        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        }

    }


    public List<String> getExtraItems(Category category) throws ExceptionHandler {

        // SQL query to retrieve all items in standard and super tickets
        String query = "SELECT item " +
                "FROM TicketItems " +
                "UNION " +
                "SELECT item " +
                "FROM SuperTicket";

        try (//Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            // List to store all names in order to return
            List<String> allItems = new ArrayList<>();

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if we got any answer from sql server
            while (resultSet.next()){
                // Get first_name from results set
                String item = resultSet.getString(category.ITEM.getValue());

                // Add the retrieved name to names list
                allItems.add(item);
            }
            return allItems;

        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        }
    }


    public boolean isCustomerExists(Customer customer) throws ExceptionHandler {

        // SQL query to retrieve email address of customer if exists
        String query = "SELECT * " +
                "FROM Customers " +
                "WHERE email = ?";


        try (//Connection conn = dbConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setString(1, customer.getEmail());
            // List to store all names in order to return
            List<String> allItems = new ArrayList<>();

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if we got any answer from sql server which means customer exists, then updates the id
            if (resultSet.next()){
                // Customer exists

                // Get and set customer id to customer object
                customer.setId(resultSet.getInt("id"));

                // Returns true because customer exists
                return true;
            } else {

                // Customer doesn't exits
                return false;
            }

        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        }
    }




    public void createCustomer(Customer customer) throws ExceptionHandler {
        String query = "INSERT INTO Customers(first_name, last_name, email, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getPhoneNumber());
            int rowsAffected = statement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected == 1) {
                // Retrieve the generated keys
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // Retrieve and return the generated ID
                    customer.setId(generatedKeys.getInt(1));
                } else {
                    // Handle the case where no keys were generated
                    throw new ExceptionHandler(ExceptionMessage.KEY_GENERATION_FAILURE.getValue());
                }
            } else {
                // Handle the case where the insertion failed
                throw new ExceptionHandler(ExceptionMessage.INSERTION_FAILED.getValue());
            }
        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        }
    }

    public void createTicket(Ticket ticket) throws ExceptionHandler {
        String ticketQuery = "INSERT INTO Tickets(customer_id, event_id, uuid) VALUES (?, ?, ?)";
        String itemQuery = "INSERT INTO TicketItems(item, is_claimed, ticket_id) VALUES (?, ?, ?)";

        // Use a transaction
        try {
            // Start the transaction
            conn.setAutoCommit(false);
            // Set transaction isolation to RepeatableRead
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                // Next, insert ticket details
                try (PreparedStatement ticketStatement = conn.prepareStatement(ticketQuery, Statement.RETURN_GENERATED_KEYS)) {
                    ticketStatement.setInt(1, ticket.getCustomer().getId());
                    ticketStatement.setInt(2, ticket.getEvent().getId());
                    ticketStatement.setString(3, ticket.getUUID().toString());
                    int ticketRowsAffected = ticketStatement.executeUpdate();

                    if (ticketRowsAffected != 1) {
                        // Rollback the transaction and throw an exception
                        conn.rollback();
                        throw new ExceptionHandler(ExceptionMessage.TICKET_INSERTION_FAILED.getValue());
                    }

                    // Retrieve the generated ticket ID
                    ResultSet ticketKeys = ticketStatement.getGeneratedKeys();
                    if (ticketKeys.next()) {
                        // Update ticket id with retrieved id database
                        ticket.setId(ticketKeys.getInt(1));
                    } else {
                        // Rollback the transaction and throw an exception
                        conn.rollback();
                        throw new ExceptionHandler(ExceptionMessage.KEY_GENERATION_FAILURE.getValue() + "\nTicket");
                    }

                    // Finally, insert ticket items
                    try (PreparedStatement itemStatement = conn.prepareStatement(itemQuery, Statement.RETURN_GENERATED_KEYS)) {
                        for (Item item : ticket.getItems()) {
                            itemStatement.setString(1, item.getTitle());
                            itemStatement.setBoolean(2, item.isClaimed());
                            itemStatement.setInt(3, ticket.getId());
                            itemStatement.addBatch();
                        }
                        // Execute batch insert
                        int[] itemRowsAffected = itemStatement.executeBatch();

                        // Check if all items were inserted successfully
                        for (int rows : itemRowsAffected) {
                            if (rows != 1) {
                                // Rollback the transaction and throw an exception
                                conn.rollback();
                                throw new ExceptionHandler(ExceptionMessage.ITEM_INSERTION_FAILED.getValue());
                            }
                            // Update ID for the item object

                        }
                        // If all steps were successful, commit the transaction
                        conn.commit();
                    }
                } catch (Exception e){
                    throw new ExceptionHandler(ExceptionMessage.INSERTION_FAILED.getValue());
                }

        } catch (SQLException ex) {
            // Connection to database failed, throw exception and message
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        } finally {
            try {
                // Restore auto-commit mode
                conn.setAutoCommit(true);
                // Restore transaction mode
                conn.setTransactionIsolation(Connection.TRANSACTION_NONE);
            } catch (SQLException ex) {
                // Handle exception or log it
                ex.printStackTrace();
            }
        }
    }


    public void insertItems(Ticket ticket) throws ExceptionHandler {
        String query = "INSERT INTO TicketItems(item, is_claimed, ticket_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            for (Item item : ticket.getItems()) {
                statement.setString(1, item.getTitle());
                statement.setBoolean(2, item.isClaimed());
                statement.setInt(3, ticket.getId());
                // Execute the statement for each item
                statement.addBatch();
            }
            // Execute the batch and retrieve the generated keys for each item
            int[] rowsAffected = statement.executeBatch();
            System.out.println(rowsAffected.length);
            conn.commit();
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_NONE);

        } catch (SQLException ex) {
            // Handle the case where the insertion failed
            throw new ExceptionHandler(
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue()
                            + "\n"
                            + ex.getMessage());
        }
    }

}
