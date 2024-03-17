package dk.easvoucher.model;

import dk.easvoucher.bll.Logic;
import dk.easvoucher.be.Event;
import dk.easvoucher.be.Ticket;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Model {
    private Logic logic;

    public Model() {
        this.logic = new Logic();
    }

    public boolean createEvent(String eventName, String description, Timestamp startTime, Timestamp endTime, String location, int coordinatorId) {
        return logic.createEvent(eventName, description, startTime, endTime, location, coordinatorId);
    }

    public List<Event> getAllEvents() {
        return logic.getAllEvents();
    }

    public boolean updateEvent(int eventId, String eventName, String description, Timestamp startTime, Timestamp endTime, String location) {
        return logic.updateEvent(eventId, eventName, description, startTime, endTime, location);
    }

    public boolean deleteEvent(int eventId) {
        return logic.deleteEvent(eventId);
    }

    public void insertTicket(String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        logic.insertTicket(ticketNumber, qrCode, barcode, UUID, ticketTypeId, eventId, customerId);
    }

    public List<Ticket> getAllTickets() throws SQLException {
        return logic.getAllTickets();
    }

    public void updateTicket(int ticketId, String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) throws SQLException {
        logic.updateTicket(ticketId, ticketNumber, qrCode, barcode, UUID, ticketTypeId, eventId, customerId);
    }

    public void deleteTicket(int ticketId) throws SQLException {
        logic.deleteTicket(ticketId);
    }

    public boolean assignCoordinator(int eventId, int coordinatorId, int assignedBy) {
        return logic.assignCoordinator(eventId, coordinatorId, assignedBy);
    }
}