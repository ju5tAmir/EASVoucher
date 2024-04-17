package dk.easvoucher.gui.dashboard.coordinator.event.update;

import dk.easvoucher.be.event.Note;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.dashboard.coordinator.CoordinatorModel;
import dk.easvoucher.utils.WindowUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.ZoneId;


public class UpdateEventController implements IController<CoordinatorModel> {
    @FXML
    private TextField nameField, locationField, startTimeField, endTimeField, noteTitleField;

    @FXML
    private DatePicker startDate, endDate;
    @FXML
    private ListView<String> notesListView;
    @FXML
    private ListView<CheckBox> allowedECsListView;
    private CoordinatorModel coordinatorModel;
    private final UpdateEventModel model = new UpdateEventModel();

    @FXML
    private void onUpdateEvent(ActionEvent actionEvent) {
        try {
            // Check for null inputs
            checkForNulls();

            // Update model object based on new values
            model.setEventName(nameField.getText());
            model.setEventLocation(locationField.getText());
            model.setStartDate(startDate.getValue());
            model.setStartTime(startTimeField.getText());
            model.setEndDate(endDate.getValue());
            model.setEndTime(endTimeField.getText());
            model.updateEvent();


            // update the coordinator model, so the list of events will be updated
            coordinatorModel.addEventToList(model.getEvent());

            // If everything went well, pop up success alert
            AlertHandler.displayAlert(ExceptionMessage.EVENT_UPDATED_SUCCESSFULLY.getValue(), Alert.AlertType.INFORMATION);

            // Close the stage
            Stage stageToClose = (Stage) nameField.getScene().getWindow();
            WindowUtils.closeStage(stageToClose);

        } catch (ExceptionHandler ex) {
            AlertHandler.displayErrorAlert(ex.getMessage());
        }
    }



    @FXML
    private void onResetFields(ActionEvent actionEvent) {
        nameField.clear();
        locationField.clear();
        startDate.setValue(null);
        startTimeField.clear();
        endDate.setValue(null);
        endTimeField.clear();
        notesListView.getItems().clear();
        clearUncheckAllCoordinators();
        model.clear();
    }

    private void clearUncheckAllCoordinators(){
        for (CheckBox cb: allowedECsListView.getItems()){
            cb.setSelected(false);
        }
    }

    @FXML
    private void onAddNote(ActionEvent actionEvent) {
        // Prevent from adding empty note
        if (!noteTitleField.getText().isEmpty()){
            notesListView.getItems().add(noteTitleField.getText());

            Note note = new Note();
            note.setNote(noteTitleField.getText());
            model.addNote(note);

            noteTitleField.clear();
        } else {
            AlertHandler.displayInformation(ExceptionMessage.EMPTY_NOTE_TITLE.getValue());
        }
    }
    @FXML
    private void onRemoveNote(ActionEvent actionEvent) {
        // Check if any note selected otherwise show alert
        if (notesListView.getSelectionModel().getSelectedItem() != null ){
            // Remove the selected item from model
            model.removeNote(notesListView.getSelectionModel().getSelectedItem());
            // Remove the selected item from the ListView
            notesListView.getItems().remove(notesListView.getSelectionModel().getSelectedItem());
        } else {
            // Display alert when there is not selected note
            AlertHandler.displayInformation(ExceptionMessage.SELECT_NOTE_FIRST.getValue());
        }
    }

    @Override
    public void setModel(CoordinatorModel coordinatorModel) {
        this.coordinatorModel = coordinatorModel;

        // Retrieve selected event object from coordinator model and set it to UpdateEventModel
        this.model.setEvent(coordinatorModel.getSelectedEvent());

        // Update fields
        nameField.setText(model.getEvent().getName());
        locationField.setText(model.getEvent().getLocation());
        startDate.setValue(new java.util.Date(model.getEvent().getStartDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        startTimeField.setText(model.getEvent().getStartTime().toString().substring(0,5));
        if (model.getEvent().getEndDate() != null) {
            endDate.setValue(new java.util.Date(model.getEvent().getEndDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (model.getEvent().getEndTime() != null){
            endTimeField.setText(model.getEvent().getEndTime().toString().substring(0,5));
        }
        for (Note note: model.getEvent().getNotes()){
            notesListView.getItems().add(note.getNote());
        }



        try {
            // Load all possible coordinators, and if selected event has already had any of them, checks the box for that coordinator
            setAllowedECsListView();
        } catch (ExceptionHandler ex){
            AlertHandler.displayErrorAlert(ex.getMessage());
        }
    }

    private void setAllowedECsListView() throws ExceptionHandler {
        // Update the ListView with coordinators name as CheckBox items
        for (Employee coordinator: model.getCoordinatorsList()) {
            CheckBox item = new CheckBox(coordinator.getUsername());

            // Check the box if a coordinator already have access to the event
            if (model.getEvent().getCoordinators().contains(coordinator)){
                item.setSelected(true);
            }

            // Add listener for checkboxes, if checked, adds coordinator to selected ones in model
            item.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    // Coordinator selected, adds to the model
                    model.addCoordinator(coordinator);
                } else{
                    // Coordinator unchecked, removes from the model
                    model.removeCoordinator(coordinator);
                }
            });
            // Add the created checkbox to the ListView
            allowedECsListView.getItems().add(item);
        }
    }




    private void checkForNulls() throws ExceptionHandler {
        StringBuilder messageBuilder = new StringBuilder();

        if (nameField.getText().isEmpty()) {
            messageBuilder.append(ExceptionMessage.EVENT_NAME_EMPTY.getValue()).append("\n");
        }

        if (locationField.getText().isEmpty()) {
            messageBuilder.append(ExceptionMessage.EVENT_LOCATION_EMPTY.getValue()).append("\n");
        }

        if (startDate.getValue() == null) {
            messageBuilder.append(ExceptionMessage.EVENT_START_DATE_EMPTY.getValue()).append("\n");
        }

        if (startTimeField.getText().isEmpty()) {
            messageBuilder.append(ExceptionMessage.EVENT_START_TIME_EMPTY.getValue()).append("\n");
        }

        String message = messageBuilder.toString().trim();
        if (!message.isEmpty()) {
            throw new ExceptionHandler(message);
        }
    }

}
