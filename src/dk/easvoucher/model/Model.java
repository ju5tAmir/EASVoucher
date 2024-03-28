package dk.easvoucher.model;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.response.Response;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.user.IUser;
import dk.easvoucher.bll.EventTicketLogic;
import dk.easvoucher.bll.LoginLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class Model {
    private LoginLogic logic = new LoginLogic();
    private EventTicketLogic eventTicketLogic = new EventTicketLogic();
    private BooleanProperty loginStatus;
    private ObservableList<Event> eventsList = FXCollections.observableArrayList();
    private ObservableList<ITicket> ticketsList = FXCollections.observableArrayList();

    private IUser user;

    public Model() {
        this.loginStatus = new SimpleBooleanProperty(false);
    }

    public void login(String username, String password) throws ExceptionHandler {
        Response response = logic.login(username, password);
        if (response.getStatus()) {
            setData(response);
        }
    }

    private void setData(Response response) {
        setLoginStatus(response.getStatus());
        setUser(response.getUser());
        setEvents(response.getEvents());
        setTickets(response.getTickets());
    }

    private void setLoginStatus(boolean status) {
        loginStatus.set(status);
    }

    public BooleanProperty getLoginStatus() {
        return loginStatus;
    }

    public IUser getUser() {
        return user;
    }

    private void setUser(IUser user) {
        this.user = user;
    }

    private void setEvents(List<Event> events) {
        this.eventsList.clear();
        this.eventsList.addAll(events);
    }

    public ObservableList<Event> getEvents() {
        return eventsList;
    }

    private void setTickets(List<ITicket> tickets) {
        this.ticketsList.clear();
        this.ticketsList.addAll(tickets);
    }

    public ObservableList<ITicket> getTickets() {
        return ticketsList;
    }

    // Methods to transit all operations from EventTicketLogic

    public void createEvent(String name, String time, String location, String notes, Integer coordinatorId, Integer adminId) throws SQLException {
        eventTicketLogic.createEvent(name, time, location, notes, coordinatorId, adminId);
    }

    public void fetchAllEvents() throws SQLException {
        List<Event> events = eventTicketLogic.readAllEvents();
        eventsList.clear();
        eventsList.addAll(events);
    }

    public void updateEvent(Integer eventId, String name, String time, String location, String notes, Integer coordinatorId, Integer adminId) throws SQLException {
        eventTicketLogic.updateEvent(eventId, name, time, location, notes, coordinatorId, adminId);
    }

    public void deleteEvent(Integer eventId) throws SQLException {
        eventTicketLogic.deleteEvent(eventId);
    }

    public void createTicket(String qrCode, String barcode, Integer typeId, Integer eventId) throws SQLException {
        eventTicketLogic.createTicket(qrCode, barcode, typeId, eventId);
    }

    public void fetchAllTickets() throws SQLException {
        List<Ticket> tickets = eventTicketLogic.readAllTickets();
        ticketsList.clear();
        ticketsList.addAll(tickets);
    }

    public void updateTicket(Integer ticketId, String qrCode, String barcode, Integer typeId, Integer eventId) throws SQLException {
        eventTicketLogic.updateTicket(ticketId, qrCode, barcode, typeId, eventId);
    }

    public void deleteTicket(Integer ticketId) throws SQLException {
        eventTicketLogic.deleteTicket(ticketId);
    }

    public void assignCoordinatorToEvent(String eventName, int coordinatorId) throws SQLException {
        eventTicketLogic.assignCoordinatorToEvent(eventName, coordinatorId);
    }
}