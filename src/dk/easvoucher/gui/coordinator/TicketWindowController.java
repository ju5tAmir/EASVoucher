package dk.easvoucher.gui.coordinator;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import dk.easvoucher.model.Model;
import dk.easvoucher.be.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketWindowController {
    public TextField ticketNumberField, qrCodeField, barcodeField, uuidField;
    public ComboBox<Integer> ticketTypeField, eventField, customerField;
    public TableView<Ticket> ticketsTable;
    public TableColumn<Ticket, Integer> ticketIdColumn;
    public TableColumn<Ticket, String> ticketNumberColumn, qrCodeColumn, barcodeColumn, uuidColumn;
    public TableColumn<Ticket, Integer> ticketTypeIdColumn, eventIdColumn, customerIdColumn;

    private Model model;

    public TicketWindowController() {
        model = new Model();
    }

    public void initialize() {
        refreshTickets();
    }

    public void handleSave(ActionEvent event) {
        try {
            String ticketNumber = ticketNumberField.getText();
            String qrCode = qrCodeField.getText();
            String barcode = barcodeField.getText();
            String uuid = uuidField.getText();
            Integer ticketTypeId = ticketTypeField.getValue();
            Integer eventId = eventField.getValue();
            Integer customerId = customerField.getValue();

            model.insertTicket(ticketNumber, qrCode, barcode, uuid, ticketTypeId, eventId, customerId);
            refreshTickets();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleUpdate(ActionEvent event) {
        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            try {
                model.updateTicket(selectedTicket.getTicketId(), ticketNumberField.getText(), qrCodeField.getText(), barcodeField.getText(), uuidField.getText(), ticketTypeField.getValue(), eventField.getValue(), customerField.getValue());
                refreshTickets();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDelete(ActionEvent event) {
        Ticket selectedTicket = ticketsTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            try {
                model.deleteTicket(selectedTicket.getTicketId());
                refreshTickets();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleClear(ActionEvent event) {
        clearForm();
    }

    private void refreshTickets() {
        try {
            List<Ticket> tickets = model.getAllTickets();
            ticketsTable.getItems().setAll(tickets);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        ticketNumberField.clear();
        qrCodeField.clear();
        barcodeField.clear();
        uuidField.clear();
        ticketTypeField.setValue(null);
        eventField.setValue(null);
        customerField.setValue(null);
    }
}
