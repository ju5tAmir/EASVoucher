package dk.easvoucher.bll;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.Item;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.ticket.TicketType;
import dk.easvoucher.be.user.Customer;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class CreateTicketLogicTest {

    private CreateTicketLogic logic;
    @BeforeEach
    void setUp() throws ExceptionHandler {
        logic = new CreateTicketLogic();
    }

    @Test
    @DisplayName("Test to create a ticket")
    void createTicket() throws ExceptionHandler {

        // Arrange
        // New ticket object
        Ticket ticket = new Ticket();

        // Customer whois buying the ticket (Hope one day he could)
        Customer customer = new Customer();
        customer.setFirstName("Ro55");
        customer.setLastName("Ulbr1cht");
        customer.setEmail("r.ulbricht@sr.on");
        customer.setPhoneNumber("+123456789");

        // Event object
        Event event = new Event();
        event.setId(1);

        // Updating the ticket object
        ticket.setCustomer(customer);
        ticket.setEvent(event);
        ticket.setTicketType(TicketType.STANDARD);

        // Assign zero item to the ticket
        List<Item> items = new ArrayList<>();
        ticket.setItems(items);


        // Act
        logic.createTicket(ticket);

        // Assert
        // If ticket created successfully, the id should not be zero, because it's updating after a successful creation
        assertNotEquals(ticket.getId(), 0);
    }



    @Test
    @DisplayName("Test failure in creating a ticket")
    public void testCreateTicketFailure() throws ExceptionHandler {

        // Arrange
        //  We can get errors by not assigning required values for the ticket.

        // Arrange
        // Create new and empty ticket object
        Ticket ticket = new Ticket();
        Customer customer = new Customer();
        customer.setEmail("r.ulbricht@sr.on");
        ticket.setCustomer(customer);

        // Act

        // Act and Assert
        // Verify that the exception is thrown when trying to create the ticket
        assertThrows(ExceptionHandler.class, () -> {
            try {
                logic.createTicket(ticket);
            } catch (ExceptionHandler ex) {
                // You can add further assertions if necessary
                assertEquals(ExceptionMessage.INSERTION_FAILED.getValue(), ex.getMessage());
                throw ex;
            }
        });
    }
}