package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.be.entities.Event;
import dk.easvoucher.model.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class EventWindowController {

    public TextField eventName, eventDescription, eventLocation, coordinatorId;
    public DatePicker startTime, endTime;
    public TableView<Event> eventsTable;
    public TableColumn<Event, Integer> eventIdColumn;
    public TableColumn<Event, String> eventNameColumn, descriptionColumn, locationColumn;
    public TableColumn<Event, LocalDateTime> startTimeColumn, endTimeColumn;
    public TableColumn<Event, Integer> coordinatorIdColumn;

    private Model model;

    public EventWindowController() {
        model = new Model();
    }

    public void initialize() {
        refreshEvents();
    }

    public void handleCreate(ActionEvent event) {
        String name = eventName.getText();
        String desc = eventDescription.getText();
        Timestamp start = Timestamp.valueOf(startTime.getValue().atStartOfDay());
        Timestamp end = Timestamp.valueOf(endTime.getValue().atStartOfDay());
        String loc = eventLocation.getText();
        int coordId = Integer.parseInt(coordinatorId.getText());

        boolean success = model.createEvent(name, desc, start, end, loc, coordId);
        if(success) {
            refreshEvents();
        }
    }

    public void handleRead(ActionEvent event) {
        refreshEvents();
    }

    public void handleUpdate(ActionEvent event) {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            boolean success = model.updateEvent(
                    selectedEvent.getEventId(),
                    eventName.getText(),
                    eventDescription.getText(),
                    Timestamp.valueOf(startTime.getValue().atStartOfDay()),
                    Timestamp.valueOf(endTime.getValue().atStartOfDay()),
                    eventLocation.getText()
            );
            if(success) {
                refreshEvents();
            }
        }
    }

    public void handleDelete(ActionEvent event) {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            boolean success = model.deleteEvent(selectedEvent.getEventId());
            if(success) {
                refreshEvents();
            }
        }
    }

    private void refreshEvents() {
        List<Event> events = model.getAllEvents();
        eventsTable.getItems().setAll(events);
    }
}