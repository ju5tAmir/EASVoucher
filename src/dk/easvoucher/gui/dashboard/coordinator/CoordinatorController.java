package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.ticket.TicketType;
import dk.easvoucher.be.user.Customer;
import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.login.LoginModel;
import dk.easvoucher.utils.PageType;
import dk.easvoucher.utils.WindowUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.ResourceBundle;

public class CoordinatorController implements IController<LoginModel>, Initializable {
    private LoginModel loginModel;
    private CoordinatorModel model;
    @FXML
    private Label welcomeLabel;

    /**
     * TableView and TableColumns declarations for Events
     */
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private TableColumn<Event, Integer> eventId;
    @FXML
    private TableColumn<Event, String> eventName;
    @FXML
    private TableColumn<Event, Date> eventStartDate;
    @FXML
    private TableColumn<Event, Time> eventStartTime;

    /**
     * TableView and TableColumn declarations for Tickets (both Standard and Super ticket)
     */
    @FXML
    private TableView<ITicket> ticketsTable;
    @FXML
    private TableColumn<Ticket, Integer> ticketId;
    @FXML
    private TableColumn<Ticket, TicketType> ticketType;
    @FXML
    private TableColumn<Ticket, String> ticketEmail;
    @FXML
    private TableColumn<Ticket, String> ticketPhoneNumber;

    @FXML
    private ListView<Label> eventInfoBox;
    @FXML
    private ListView<Label> ticketInfoBox;
    @FXML
    private AnchorPane mainAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setModel(LoginModel loginModel)
    {
        try {
            // Update value of the LoginModel in here for lateral usages
            this.loginModel = loginModel;

            // Instantiate a new CoordinatorModel object
            this.model = new CoordinatorModel();

            // Set the coordinator for the CoordinatorModel using the logged-in coordinator from the LoginModel
            model.setCoordinator(loginModel.getLoggedInEmployee());

            // Set welcome note
            setWelcomeLabel();

            // Set events table and columns based on allowed events for the coordinator
            setEventsTable();

            // Set tickets table and columns based on selected event on EventsTable
            // setTicketsTable();
        } catch (ExceptionHandler ex){
            AlertHandler.displayErrorAlert(ex.getMessage());
        }

    }




    @FXML
    private void createEventButton(ActionEvent actionEvent) throws ExceptionHandler {
        // New Stage for create event window
        Stage stage = new Stage();

        WindowUtils.createStage(stage, PageType.CREATE_EVENT, model, Modality.APPLICATION_MODAL);
    }

    @FXML
    private void updateEventButton(ActionEvent actionEvent) throws ExceptionHandler{
        // Selected event from events table
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();

        // Check if there is any event selected, to update event for that
        if (selectedEvent != null) {
            // Updates the selected event object in the model
            model.setSelectedEvent(eventsTable.getSelectionModel().getSelectedItem());

            // New stage for update ticket page
            Stage stage = new Stage();

            // Create new stage for event update and blocking the coordinator dashboard through modality
            // also its passing CoordinatorModel object to UpdateEventController
            WindowUtils.createStage(stage, PageType.UPDATE_EVENT, this.model, Modality.APPLICATION_MODAL);
        } else {
            // If there is no event selected
            AlertHandler.displayAlert(ExceptionMessage.NO_EVENT_SELECTED.getValue(), Alert.AlertType.INFORMATION);
        }
    }


    @FXML
    private void deleteEventButton(ActionEvent actionEvent) {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        try {
        if (selectedEvent != null){
            model.deleteEvent(selectedEvent);
            } else {
            AlertHandler.displayAlert(ExceptionMessage.SELECT_AN_EVENT.getValue(), Alert.AlertType.INFORMATION);
        }
        } catch (ExceptionHandler exceptionHandler){
            AlertHandler.displayErrorAlert(exceptionHandler.getMessage());
        }
    }

    @FXML
    private void manageECsButton(ActionEvent actionEvent) {
    }


    @FXML
    private void createTicketButton(ActionEvent actionEvent) throws ExceptionHandler {
        // Selected event from events table
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();

        // Check if there is any event selected, to create event for that
        if (selectedEvent != null) {
            // Updates the selected event object in the model
            model.setSelectedEvent(eventsTable.getSelectionModel().getSelectedItem());

            // New stage for create ticket page
            Stage stage = new Stage();

            // Create new stage for ticket creation and blocking the coordinator dashboard through modality
            // also its passing CoordinatorModel object to CreateTicketController
            WindowUtils.createStage(stage, PageType.CREATE_TICKET, this.model, Modality.APPLICATION_MODAL);
        } else {
            // If there is no event selected
            AlertHandler.displayAlert(ExceptionMessage.NO_EVENT_SELECTED.getValue(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void updateTicketButton(ActionEvent actionEvent) throws ExceptionHandler {

    }


    @FXML
    private void deleteTicketButton(ActionEvent actionEvent) {
        try {
            Ticket ticket = (Ticket) ticketsTable.getSelectionModel().getSelectedItem();
            if (ticket != null) {
                model.deleteTicket(ticket);
            } else {
                AlertHandler.displayAlert(ExceptionMessage.SELECT_A_TICKET.getValue(), Alert.AlertType.INFORMATION);
            }
        } catch (ExceptionHandler ex) {
            AlertHandler.displayErrorAlert(ex.getMessage());
        }
    }

    private void setWelcomeLabel(){
        welcomeLabel.setText("Welcome " + model.getCoordinator().getUsername());
    }

    private void setEventsTable() throws ExceptionHandler{
        // Try to set TableView or throw an error
        eventsTable.setItems(model.getEvents());

        // Set the values from Event objects in to the table columns
        eventId.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        eventStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));


        // Set up selection listener for table items
        eventsTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldSelectedEvent, newSelectedEvent) ->{
            // If selected event is not null
            if (newSelectedEvent != null){
                // Send info box VBox and Event object to be prepared to show
                model.setInfoBox(eventInfoBox, newSelectedEvent);
                // Set tickets table based on the selected event
                try {
                    ticketsTable.setItems(model.getAllTicketsForEvent(newSelectedEvent));
                } catch (ExceptionHandler e) {
                    AlertHandler.displayErrorAlert(e.getMessage());
                }
                setTicketsTable();
            }
        }));

        // Set listener for ticket selection
        ticketsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelectedTicket, newSelectedTicket) -> {
            if (newSelectedTicket != null) {
                Ticket t = (Ticket) newSelectedTicket;
                // Send info box ListView and Ticket object to be prepared to show
                model.setTicketInfoBox(ticketInfoBox, t);
            }
            else {
                // Clear Ticket info box when nothing is selected
                ticketInfoBox.getItems().clear();
            }
        });
    }

    private void setTicketsTable(){

        ticketId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticketType.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        ticketEmail.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            String email = customer.getEmail();
            return new SimpleStringProperty(email);
        });
        ticketPhoneNumber.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            String phoneNumber = customer.getPhoneNumber();
            return new SimpleStringProperty(phoneNumber);
        });
    }


}
