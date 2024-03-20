package dk.easvoucher.bll;

import dk.easvoucher.be.entities.Event;
import dk.easvoucher.be.entities.Ticket;
import dk.easvoucher.dal.CoordinatorDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class CoordinatorLogic {
    private final CoordinatorDAO coordinatorDAO;

    public CoordinatorLogic() {
        this.coordinatorDAO = new CoordinatorDAO();
    }

    public boolean createEvent(String eventName, String description, Timestamp startTime, Timestamp endTime, String location, int coordinatorId) {
        return coordinatorDAO.createEvent(eventName, description, startTime, endTime, location, coordinatorId);
    }

    public List<Event> getAllEvents() {
        return coordinatorDAO.getAllEvents();
    }

    public boolean updateEvent(int eventId, String eventName, String description, Timestamp startTime, Timestamp endTime, String location) {
        return coordinatorDAO.updateEvent(eventId, eventName, description, startTime, endTime, location);
    }

    public boolean deleteEvent(int eventId) {
        return coordinatorDAO.deleteEvent(eventId);
    }

    public void insertTicket(String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        coordinatorDAO.insertTicket(ticketNumber, qrCode, barcode, UUID, ticketTypeId, eventId, customerId);
    }

    public List<Ticket> getAllTickets() throws SQLException {
        return coordinatorDAO.getAllTickets();
    }

    public void updateTicket(int ticketId, String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        coordinatorDAO.updateTicket(ticketId, ticketNumber, qrCode, barcode, UUID, ticketTypeId, eventId, customerId);
    }

    public void deleteTicket(int ticketId) throws SQLException {
        coordinatorDAO.deleteTicket(ticketId);
    }

    public boolean assignCoordinator(int eventId, int coordinatorId, int assignedBy) {
        return coordinatorDAO.assignCoordinator(eventId, coordinatorId, assignedBy);
    }
}