package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.model.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AccessWindowController {
    public TextField eventIdField, coordinatorIdField, assignedByField;
    public TableView table;
    public TableColumn accessEntryIdColumn, eventIdColumn, coordinatorIdColumn, assignedByColumn;

    private Model model;

    public AccessWindowController() {
        model = new Model();
    }

    public void initialize() {

    }

    public void handleAdd(ActionEvent event) {
        try {
            int eventId = Integer.parseInt(eventIdField.getText());
            int coordinatorId = Integer.parseInt(coordinatorIdField.getText());
            int assignedBy = Integer.parseInt(assignedByField.getText());

            boolean success = model.assignCoordinator(eventId, coordinatorId, assignedBy);
            if (success) {
                clearFormFields();
            } else {
            }
        } catch (NumberFormatException e) {
        }
    }
    private void clearFormFields() {
        eventIdField.clear();
        coordinatorIdField.clear();
        assignedByField.clear();
    }
}