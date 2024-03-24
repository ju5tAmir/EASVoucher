package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.be.entities.Event;
import dk.easvoucher.be.entities.Ticket;
import dk.easvoucher.model.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketWindowController {
    public TextField ticketNumberField, qrCodeField, barcodeField, uuidField;
    public ComboBox<String> ticketTypeField, eventField;
    public ComboBox<Integer> customerField;
    private Map<String, Integer> ticketTypeMap = new HashMap<>();
    private Map<String, Integer> eventNames = new HashMap<>();
    public TableView<Ticket> ticketsTable;
    public TableColumn<Ticket, Integer> ticketIdColumn;
    public TableColumn<Ticket, String> ticketNumberColumn, qrCodeColumn, barcodeColumn, uuidColumn;
    public TableColumn<Ticket, Integer> ticketTypeIdColumn, eventIdColumn, customerIdColumn;

    private Model model;

    public TicketWindowController() {
        model = new Model();

        ticketTypeMap.put("Participant", 1);
        ticketTypeMap.put("Customizable", 2);
        ticketTypeMap.put("VIP ticket", 3);
        ticketTypeMap.put("1st row", 4);
        ticketTypeMap.put("free beer", 5);

        initializeEventComboBox();
    }

    public void initializeEventComboBox() {
        List<Event> events = model.getAllEvents();
        for (Event event : events) {
            eventNames.put(event.getEventName(), event.getEventId());
        }
    }

    public void initialize() throws SQLException {
        setTable();
        setColumns();
        refreshTickets();

        ObservableList<String> ticketTypes = FXCollections.observableArrayList(ticketTypeMap.keySet());
        ticketTypeField.setItems(ticketTypes);

        ObservableList<String> eventNameTypes = FXCollections.observableArrayList(eventNames.keySet());
        eventField.setItems(eventNameTypes);

        List<Ticket> tickets = model.getAllTickets();
        for (Ticket ticket : tickets) {
            customerField.getItems().add(ticket.getCustomerId());
        }
    }


    private void setTable() throws SQLException {
        List<Ticket> tickets = model.getAllTickets();
        ObservableList<Ticket> observableList= FXCollections.observableArrayList(tickets);
        ticketsTable.setItems(observableList);
    }

    private void setColumns(){
        ticketIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));
        qrCodeColumn.setCellValueFactory(new PropertyValueFactory<>("qrCode"));
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        uuidColumn.setCellValueFactory(new PropertyValueFactory<>("UUID"));
        ticketTypeIdColumn.setCellValueFactory(new PropertyValueFactory<>("ticketTypeId"));
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
    public void handleSave(ActionEvent event) {
        try {
            String ticketNumber = ticketNumberField.getText();
            String qrCode = qrCodeField.getText();
            String barcode = barcodeField.getText();
            String uuid = uuidField.getText();

            String ticketTypeString = ticketTypeField.getValue();
            Integer ticketTypeId = ticketTypeMap.get(ticketTypeString);

            String eventTypeString = eventField.getValue();
            Integer eventId = eventNames.get(eventTypeString);

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
                String ticketNumber = ticketNumberField.getText();
                String qrCode = qrCodeField.getText();
                String barcode = barcodeField.getText();
                String uuid = uuidField.getText();

                String ticketTypeString = ticketTypeField.getValue();
                Integer ticketTypeId = ticketTypeMap.get(ticketTypeString);

                String eventTypeString = eventField.getValue();
                Integer eventId = eventNames.get(eventTypeString);

                Integer customerId = customerField.getValue();

                model.updateTicket(selectedTicket.getTicketId(), ticketNumber, qrCode, barcode, uuid, ticketTypeId, eventId, customerId);
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
        List<Ticket> tickets = model.getAllTickets();
        ticketsTable.getItems().setAll(tickets);
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