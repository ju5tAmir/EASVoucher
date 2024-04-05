package dk.easvoucher.bll;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.user.User;
import dk.easvoucher.dal.CoordinatorDAO;
import dk.easvoucher.dal.EventDAO;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;

public class EventTicketLogic {
    private CoordinatorDAO coordinatorDAO;
    private EventDAO eventDAO;

    public List<Event> getAllEvents() throws SQLException {
        return eventDAO.getAllEvents();
    }

    public EventTicketLogic() {
        this.coordinatorDAO = new CoordinatorDAO();
        this.eventDAO= new EventDAO();
    }

    public void createEvent(String name, String time, String location, String notes, Integer coordinatorId, Integer adminId) throws SQLException {
        coordinatorDAO.createEvent(name, time, location, notes, coordinatorId, adminId);
    }

    public List<Event> readAllEvents() throws SQLException {
        return coordinatorDAO.readAllEvents();
    }

    public void updateEvent(Integer eventId, String name, String time, String location, String notes, Integer coordinatorId, Integer adminId) throws SQLException {
        coordinatorDAO.updateEvent(eventId, name, time, location, notes, coordinatorId, adminId);
    }

    public void deleteEvent(int eventId) throws SQLException {
        eventDAO.deleteEvent(eventId);
    }




    public void createTicket(String qrCode, String barcode, Integer typeId, Integer eventId) throws SQLException {
        coordinatorDAO.createTicket(qrCode, barcode, typeId, eventId);
    }

    public List<Ticket> readAllTickets() throws SQLException {
        return coordinatorDAO.readAllTickets();
    }

    public void updateTicket(Integer ticketId, String qrCode, String barcode, Integer typeId, Integer eventId) throws SQLException {
        coordinatorDAO.updateTicket(ticketId, qrCode, barcode, typeId, eventId);
    }

    public void deleteTicket(Integer ticketId) throws SQLException {
        coordinatorDAO.deleteTicket(ticketId);
    }

    public void assignCoordinatorToEvent(String eventName, int coordinatorId) throws SQLException {
        coordinatorDAO.assignCoordinatorToEvent(eventName, coordinatorId);
    }
}