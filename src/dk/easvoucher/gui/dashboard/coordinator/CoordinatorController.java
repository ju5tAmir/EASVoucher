package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CoordinatorController implements IController {
    public TextField eventName;
    public TextField eventTime;
    public TextField eventLocation;
    public TextField eventNotes;
    public TextField coordinatorId;
    public TextField ticketQrCode;
    public TextField ticketBarcode;
    public TextField ticketTypeId;
    public TextField ticketEventId;
    public TextField assignEventName;
    public TextField assignCoordinatorId;
    public TextField eventId;
    public TextField ticketId;
    public TextField customerId;
    private Model model;
    @FXML
    private Label customLabel;

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    public void createEvent(ActionEvent event) {
        try {
            model.createEvent(Integer.valueOf(eventId.getText()), eventName.getText(), eventTime.getText(), eventLocation.getText(), eventNotes.getText(),
                    Integer.parseInt(coordinatorId.getText()));
            showAlert("Success", "Event created successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to create event: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void fetchAllEvents(ActionEvent event) {
        try {
            model.fetchAllEvents();
            showAlert("Success", "Events fetched successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch events: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void updateEvent(ActionEvent event) {
        try {
            model.updateEvent(Integer.parseInt(eventId.getText()), eventName.getText(), eventTime.getText(), eventLocation.getText(), eventNotes.getText(),
                    Integer.parseInt(coordinatorId.getText()));
            showAlert("Success", "Event updated successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to update event: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void deleteEvent(ActionEvent event) {
        try {
            model.deleteEvent(Integer.parseInt(eventId.getText()));
            showAlert("Success", "Event deleted successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to delete event: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void createTicket(ActionEvent event) {
        try {
            model.createTicket(Integer.parseInt(ticketId.getText()), ticketQrCode.getText(), ticketBarcode.getText(),
                    Integer.parseInt(ticketTypeId.getText()), Integer.parseInt(ticketEventId.getText()), Integer.valueOf(customerId.getText()));
            showAlert("Success", "Ticket created successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to create ticket: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void fetchAllTickets(ActionEvent event) {
        try {
            model.fetchAllTickets();
            showAlert("Success", "Tickets fetched successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to fetch tickets: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void updateTicket(ActionEvent event) {
        try {
            model.updateTicket(Integer.parseInt(ticketId.getText()), ticketQrCode.getText(), ticketBarcode.getText(),
                    Integer.parseInt(ticketTypeId.getText()), Integer.parseInt(ticketEventId.getText()), Integer.valueOf(customerId.getText()));
            showAlert("Success", "Ticket updated successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to update ticket: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void deleteTicket(ActionEvent event) {
        try {
            model.deleteTicket(Integer.parseInt(ticketId.getText()));
            showAlert("Success", "Ticket deleted successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to delete ticket: " + e.getMessage(), AlertType.ERROR);
        }
    }

    public void assignCoordinatorToEvent(ActionEvent event) {
        try {
            model.assignCoordinatorToEvent(assignEventName.getText(), Integer.parseInt(assignCoordinatorId.getText()));
            showAlert("Success", "Coordinator assigned to event successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to assign coordinator: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}