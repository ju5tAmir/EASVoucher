package dk.easvoucher.model.model;

import dk.easvoucher.be.entities.Event;
import dk.easvoucher.be.entities.Ticket;
import dk.easvoucher.bll.CoordinatorLogic;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Model {
    private CoordinatorLogic coordinatorLogic;

    public Model() {
        this.coordinatorLogic = new CoordinatorLogic();
    }

    public boolean createEvent(String eventName, String description, Timestamp startTime, Timestamp endTime, String location, int coordinatorId) {
        return coordinatorLogic.createEvent(eventName, description, startTime, endTime, location, coordinatorId);
    }

    public List<Event> getAllEvents() {
        return coordinatorLogic.getAllEvents();
    }

    public boolean updateEvent(int eventId, String eventName, String description, Timestamp startTime, Timestamp endTime, String location) {
        return coordinatorLogic.updateEvent(eventId, eventName, description, startTime, endTime, location);
    }

    public boolean deleteEvent(int eventId) {
        return coordinatorLogic.deleteEvent(eventId);
    }

    public void insertTicket(String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        coordinatorLogic.insertTicket(ticketNumber, qrCode, barcode, UUID, ticketTypeId, eventId, customerId);
    }

    public List<Ticket> getAllTickets() throws SQLException {
        return coordinatorLogic.getAllTickets();
    }

    public void updateTicket(int ticketId, String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        coordinatorLogic.updateTicket(ticketId, ticketNumber, qrCode, barcode, UUID, ticketTypeId, eventId, customerId);
    }

    public void deleteTicket(int ticketId) throws SQLException {
        coordinatorLogic.deleteTicket(ticketId);
    }

    public boolean assignCoordinator(int eventId, int coordinatorId, int assignedBy) {
        return coordinatorLogic.assignCoordinator(eventId, coordinatorId, assignedBy);
    }
}