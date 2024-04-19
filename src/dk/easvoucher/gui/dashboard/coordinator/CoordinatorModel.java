package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.event.Note;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.bll.CoordinatorLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorModel {
    // List of available(allowed) events for the coordinator
    private final ObservableList<Event> events = FXCollections.observableArrayList();

    // List of tickets for the selected event
    private final ObservableList<ITicket> tickets = FXCollections.observableArrayList();

    // List of available coordinators for the selected event
    private final ObservableList<Employee> allowedCoordinators = FXCollections.observableArrayList();

    // SimpleObjectProperty to hold logged-in coordinator object
    private final SimpleObjectProperty<Employee> coordinator = new SimpleObjectProperty<>();

    // Event that needs to create ticket for it
    private final SimpleObjectProperty<Event> selectedEvent = new SimpleObjectProperty<>();


    // Instance of CoordinatorLogic
    private CoordinatorLogic logic;

    /**
     * Default constructor for CoordinatorModel
     *
     */
    public CoordinatorModel() {
        this.logic = new CoordinatorLogic();

    }

    /**
     * Sets the coordinator object property by logged-in Coordinator object
     */
    public void setCoordinator(Employee coordinator){
        this.coordinator.set(coordinator);
    }


    public ObservableList<Event> getEvents() throws ExceptionHandler {
        // Clear the previous events from the list (if anything exists) and update it with available events for the logged-in coordinator
        events.setAll(logic.getAllEvents(coordinator.get()));

        // returns the available events list for the coordinator
        return events;
    }

    public ObservableList<ITicket> getAllTicketsForEvent(Event event) throws ExceptionHandler {
        // Clear the previous tickets and set fill the array with new tickets
        tickets.setAll(logic.getAllTickets(event));

        // returns new tickets list
        return tickets;
    }

    public ObservableList<Employee> getAllowedCoordinators() {
        return allowedCoordinators;
    }

    public Employee getCoordinator() {
        return coordinator.get();
    }

    public SimpleObjectProperty<Employee> coordinatorProperty() {
        return coordinator;
    }

    public void setInfoBox(ListView<Label> eventInfoBox, Event event) {
        logic.setInfoBox(eventInfoBox, event);
    }

    public void setTicketInfoBox(ListView<Label> ticketInfoBox, Ticket ticket) {
        logic.setTicketInfoBox(ticketInfoBox, ticket);
    }

    public Event getSelectedEvent() {
        return selectedEvent.get();
    }
    public void setSelectedEvent(Event event){
        this.selectedEvent.set(event);
    }

    public void deleteEvent(Event event) throws ExceptionHandler {
        logic.deleteEvent(event);
        events.remove(event);
    }

    public void deleteTicket(Ticket ticket) throws ExceptionHandler {
        logic.deleteTicket(ticket);
        tickets.remove(ticket);
    }

    public void addEventToList(Event event){
        events.add(event);
    }

    public void addEventToList(Event event, boolean isUpdate){
        events.set(events.indexOf(selectedEvent.get()) ,event);
    }
}
