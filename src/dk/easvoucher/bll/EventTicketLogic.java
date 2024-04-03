package dk.easvoucher.bll;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.dal.CoordinatorDAO;
import java.sql.SQLException;
import java.util.List;

public class EventTicketLogic {
    private CoordinatorDAO coordinatorDAO;

    public EventTicketLogic() {
        this.coordinatorDAO = new CoordinatorDAO();
    }

    public void createEvent(Integer event_id, String name, String time, String location, String notes, Integer coordinatorId) throws SQLException {
        coordinatorDAO.createEvent(event_id, name, time, location, notes, coordinatorId);
    }

    public List<Event> readAllEvents() throws SQLException {
        return coordinatorDAO.readAllEvents();
    }

    public void updateEvent(Integer eventId, String name, String time, String location, String notes, Integer coordinatorId) throws SQLException {
        coordinatorDAO.updateEvent(eventId, name, time, location, notes, coordinatorId);
    }

    public void deleteEvent(Integer eventId) throws SQLException {
        coordinatorDAO.deleteEvent(eventId);
    }

    public void createTicket(Integer ticket_id, String qrCode, String barcode, Integer typeId, Integer eventId, Integer customer_id) throws SQLException {
        coordinatorDAO.createTicket(ticket_id, qrCode, barcode, typeId, eventId, customer_id);
    }

    public List<Ticket> readAllTickets() throws SQLException {
        return coordinatorDAO.readAllTickets();
    }

    public void updateTicket(Integer ticketId, String qrCode, String barcode, Integer typeId, Integer eventId, Integer customer_id) throws SQLException {
        coordinatorDAO.updateTicket(ticketId, qrCode, barcode, typeId, eventId, customer_id);
    }

    public void deleteTicket(Integer ticketId) throws SQLException {
        coordinatorDAO.deleteTicket(ticketId);
    }

    public void assignCoordinatorToEvent(String eventName, int coordinatorId) throws SQLException {
        coordinatorDAO.assignCoordinatorToEvent(eventName, coordinatorId);
    }
}