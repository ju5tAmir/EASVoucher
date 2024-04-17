package dk.easvoucher.dal;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.event.Note;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.ticket.Item;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.ticket.TicketType;
import dk.easvoucher.be.user.Customer;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
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

public class CoordinatorDAO {

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


    public CoordinatorDAO() {
//        databaseConnection = new DBConnection();
    }


    /**
     * Retrieve the events that a coordinator allowed to access.
     *
     * @param coordinator The coordinator for whom events need to be retrieved.
     * @return List of events that the coordinator is allowed to access.
     * @throws ExceptionHandler throws exception if there is any error with database connection.
     */
    public List<Event> getEventsForCoordinator(Employee coordinator) throws ExceptionHandler{
        // List to store retrieved events object from the database
        List<Event> events = new ArrayList<>();

        // SQL query to retrieve events accessible by the coordinator
        String query = "SELECT Events.*" +
                "FROM Events " +
                "JOIN Employees " +
                "ON Employees.id = Events.created_by " +
                "WHERE Employees.username = ?";

        // Try to establish a SQL connection and execute the query
        try (//Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
            ) {
            // Set username parameter by coordinator's username
            preparedStatement.setString(1, coordinator.getUsername());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();


            // Iterate over result set
            while (resultSet.next()){
                // Create an Event object to store event details
                Event event = new Event();

                // ** Set Event attributes ** \\
                // Get and set event id
                event.setId(resultSet.getInt("id"));
                // Get and set event title/name
                event.setName(resultSet.getString("title"));
                // Get and set event location
                event.setLocation(resultSet.getString("location"));
                // Get and set event start date
                event.setStartDate(resultSet.getDate("start_date"));
                // Get and set event start time
                event.setStartTime(resultSet.getTime("start_time"));
                // Get and set event end date (if defined, otherwise null)
                event.setEndDate(resultSet.getDate("end_date"));
                // Get and set event end time (if defined, otherwise null)
                event.setEndTime(resultSet.getTime("end_time"));


                // ** Retrieve event notes for the event ** \\
                List<Note> notes = getEventNotes(event, conn);
                // Set notes for the event
                event.setNotes(notes);

                // ** Retrieve the head coordinator (event creator) for the event ** \\
                Employee headCoordinator = getHeadCoordinator(event, conn);
                // Set head coordinator (event creator) for the event
                event.setCreatedBy(headCoordinator);

                // ** Retrieve allowed coordinators for the event ** \\
                List<Employee> allowedCoordinators = getAllowedCoordinators(event, conn);
                // Set allowed coordinators for the event object
                event.setCoordinators(allowedCoordinators);

                // Add the event object to the events list
                events.add(event);

            }

            // Return the list of events accessible by the coordinator
            return events;

        } catch (SQLException | ExceptionHandler ex) {
            // Throw an exception if there is any error with the database connection
            throw new ExceptionHandler(
                    // Database failure message
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue() +
                            // New line
                            "\n" +
                            // Error details
                            ex.getMessage());
        }
    }


    /**
     * Retrieve list of Notes for a specific event.
     *
     * @param event The event for which to retrieve notes.
     * @param conn
     * @return List of notes for the requested event.
     * @throws ExceptionHandler If there is any error database connection or anything.
     */
    public List<Note> getEventNotes(Event event, Connection conn) throws ExceptionHandler{
        // List to store event notes for the specified event
        List<Note> notes = new ArrayList<>();

        // SQL query to retrieve notes for the specified event by event ID
        String query = "SELECT EventNotes.*" +
                "FROM EventNotes " +
                "JOIN Events " +
                "ON EventNotes.event_id = Events.id " +
                "WHERE Events.id = ?";

        // Try to establish a SQL connection and execute the query
        try ( //Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            // Set event ID as a statement parameter
            preparedStatement.setInt(1, event.getId());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate over result set
            while (resultSet.next()) {
                // Create a Note object to store note details
                Note note = new Note();

                // Get and set note id
                note.setId(resultSet.getInt("id"));
                // Get and set note text
                note.setNote(resultSet.getString("note"));

                // Add note object to the list of notes
                notes.add(note);
            }

            // Return the list of notes for the requested event (can be empty if there are no notes for the requested event)
            return notes;

        } catch (Exception ex) {
            // Throw an exception if there is any error with the database connection
            throw new ExceptionHandler(
                    // Database failure message
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue() +
                            "\n" +
                            // Failure message details
                            ex.getMessage());
        }
    }


    /**
     * Retrieve head coordinator for an event.
     * This method fetches details of the head coordinator (event coordinator) for a given event
     *
     * @param event The event for which to retrieve the head coordinator.
     * @param conn
     * @return The head coordinator object for the specified event.
     * @throws ExceptionHandler If there is any issue with database connection, or coordinator not found in database.
     */
    public Employee getHeadCoordinator(Event event, Connection conn) throws ExceptionHandler{
        // SQL query to retrieve the event creator details by event id
        String query = "SELECT Employees.*" +
                "FROM Employees " +
                "JOIN Events " +
                "ON Employees.id = Events.created_by " +
                "WHERE Events.id = ?";

        // Try to establish a SQL connection and execute the query
        try (//Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            // Set event id as a statement parameter
            preparedStatement.setInt(1, event.getId());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check for results
            if (resultSet.next()){
                // Create Employee (coordinator) object to store details
                Employee coordinator = new Employee();

                // Get and set id for the coordinator
                coordinator.setId(resultSet.getInt("id"));
                // Get and set username for the coordinator
                coordinator.setUsername(resultSet.getString("username"));
                // Get role from DB as string and get enum value for that role in UserRole enum, then set it to role parameter
                coordinator.setRole(UserRole.fromString(resultSet.getString("role")));

                // Return the coordinator object
                return coordinator;

            } else {
                // Throws exception if no coordinator found
                throw new ExceptionHandler(
                        ExceptionMessage.COORDINATOR_NOT_FOUND.getValue());
            }
        } catch (SQLException | ExceptionHandler ex) {
            // Throw an exception if there is any error with the database connection
            throw new ExceptionHandler(
                    // Database failure message
                    ExceptionMessage.DB_CONNECTION_FAILURE.getValue() +
                            // new line
                            "\n" +
                            // Failure message details
                            ex.getMessage());
        }
    }


    /**
     * Fetches allowed coordinators for a specific event.
     * This method retrieves the list of allowed coordinators for the specified event.
     *
     * @param event The event for which to fetch allowed coordinators.
     * @param conn
     * @return List of allowed Employee (Coordinator) objects for the requested event.
     * @throws ExceptionHandler If there is any issue with database connection.
     */
    public List<Employee> getAllowedCoordinators(Event event, Connection conn) throws ExceptionHandler{
        // List to store allowed coordinators for the requested event
        List<Employee> allowedCoordinators = new ArrayList<>();

        // SQL query to select allowed coordinators for an event by event ID
        String query = "SELECT Employees.* " +
                "FROM Employees " +
                "JOIN EventCoordinators " +
                "AS ECS " +
                "ON ECS.coordinator_id = Employees.id " +
                "WHERE ECS.event_id = ?;";

        // Try to establish sql connection and execute the query
        try (// Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            // Set event ID parameter to statement
            preparedStatement.setInt(1, event.getId());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate over results (If any)
            while (resultSet.next()){
                // Creates Employee (coordinator) object to store details
                Employee coordinator = new Employee();

                // Get and set coordinator id
                coordinator.setId(resultSet.getInt("id"));
                // Get and set coordinator username
                coordinator.setUsername(resultSet.getString("username"));
                // Get role from DB as string and get enum value for that role in UserRole enum, then set it to role parameter
                coordinator.setRole(UserRole.fromString(resultSet.getString("role")));

                // Add coordinator object to the list of allowed coordinators
                allowedCoordinators.add(coordinator);
            }

            // Return the list of allowed coordinators including head coordinator
            return allowedCoordinators;


        } catch (Exception ex) {
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

    public List<ITicket> getAllTickets(Event event) throws ExceptionHandler{
        // List to store all tickets
        List<ITicket> tickets = new ArrayList<>();

        // SQL query to select all tickets by provided event id
        String query = "SELECT * " +
                "FROM Tickets " +
                "WHERE Tickets.event_id = ?;";

        // Try to establish sql connection and execute the query
        try (//Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)
        ) {
            // Set event ID parameter to statement
            preparedStatement.setInt(1, event.getId());

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate over result set
            while (resultSet.next()){
                // Ticket object
                Ticket ticket = new Ticket();
                // Customer object for the ticket
                Customer customer = new Customer();

                // Update ticket object
                ticket.setId(resultSet.getInt("id"));
                ticket.setTicketType(TicketType.STANDARD);
                ticket.setUUID(UUID.fromString(resultSet.getString("uuid")));
                customer.setId(resultSet.getInt("customer_id"));
                ticket.setCustomer(customer);

//                // Update ticket with items
                ticket.setItems(getTicketItems(ticket));

                // Add ticket to list of tickets
                // TODO: needs better comments
                getCustomer(ticket);
                tickets.add(ticket);
                ticket.setEvent(event);
            }
            // Return list of tickets
            return tickets;

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

    public void deleteEvent(Event event) throws ExceptionHandler {
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

    public void deleteTicket(Ticket ticket) throws ExceptionHandler {
        String[] deleteQueries = {
                "DELETE FROM CustomerTickets WHERE ticket_id = ?",
                "DELETE FROM EventTickets WHERE ticket_id = ?",
                "DELETE FROM TicketItems WHERE ticket_id = ?",
                "DELETE FROM Tickets WHERE id = ?"
        };

        try {
            conn.setAutoCommit(false);

            // Execute each delete query with batch
            for (String query : deleteQueries) {
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    // Set ticket ID parameter
                    preparedStatement.setInt(1, ticket.getId());
                    // Add query to the batch
                    preparedStatement.addBatch();
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
