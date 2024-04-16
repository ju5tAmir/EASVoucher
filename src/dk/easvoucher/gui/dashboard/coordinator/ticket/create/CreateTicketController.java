package dk.easvoucher.gui.dashboard.coordinator.ticket.create;

import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.dashboard.coordinator.CoordinatorModel;
import dk.easvoucher.utils.PageType;
import dk.easvoucher.utils.WindowUtils;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;



public class CreateTicketController implements IController<CoordinatorModel>, Initializable {



    @FXML
    private VBox firstNameVBox, lastNameVBox, emailVBox, phoneNumberVBox, itemsVBox;

    @FXML
    private TextField firstNameField, lastNameField, emailAddressField, phoneNumberLabel, itemNameField;
    @FXML
    private ListView<String> suggestions;
    @FXML
    private ListView<String> selectedItems;

    // Needed to retrieve information about allowed events, logged-in coordinator, and update the coordinator model after creating a ticket
    private CoordinatorModel coordinatorModel;

    private ChangeListener<String> autoCompleteListener;
    private CreateTicketModel model;

    @FXML
    private ListView<String> autofillListView;

    @FXML
    private TextField eventNameLabel;

    // Retrieves information from coordinator model such as logged-in coordinator, allowed events for the coordinator, ...
    @Override
    public void setModel(CoordinatorModel coordinatorModel) {

        try {

            this.coordinatorModel = coordinatorModel;
            // Instantiate the model
            this.model = new CreateTicketModel();

            // Initiate ListView
            suggestions = new ListView<>();
            // Link suggestions ListView to MatchedItems observable list in the model which contains list of matched items based on user input
            suggestions.setItems(model.getMatchedItems());
            // Initially invisible, because it doesn't have any value
            suggestions.setVisible(false);
            // Set preferred size of items in suggestion to TextField's width and 48 (size of 2 items) to the height
            suggestions.setPrefSize(firstNameField.getWidth(), 48);

            // ** Initializing Selected Items ListView only for having multiple items for ticket **
            // ** It's only created for to store special items for a ticket, like: free beer, first row, ... **
            selectedItems = new ListView<>();
            // Link selectedItems ListView to the observable list in the model which will be updated after selecting an item
            selectedItems.setItems(model.getSelectedItems());
            // Initially invisible, because it doesn't have any value
            selectedItems.setVisible(false);
            // Set maximum height to 48 (size of 2 items) to not have conflicts with suggestions ListView, otherwise it will ruin the GUI by having a long list
            selectedItems.setMaxHeight(48);


            activateAutoCompleteOnFocus(Category.FIRST_NAME, firstNameField, firstNameVBox);
            activateAutoCompleteOnFocus(Category.LAST_NAME, lastNameField, lastNameVBox);
            activateAutoCompleteOnFocus(Category.EMAIL, emailAddressField, emailVBox);
            activateAutoCompleteOnFocus(Category.PHONE_NUMBER, phoneNumberLabel, phoneNumberVBox);
            activateAutoCompleteOnFocus(Category.ITEM, itemNameField, itemsVBox);
        } catch (ExceptionHandler ex){
            AlertHandler.displayErrorAlert(ex.getMessage());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onCreateTicketButton(ActionEvent actionEvent) {
        // Check for null inputs

        try {
            checkForNulls();
            model.setCustomerFirstName(firstNameField.getText());
            model.setCustomerLastName(lastNameField.getText());
            model.setCustomerEmail(emailAddressField.getText());
            model.setCustomerPhoneNumber(phoneNumberLabel.getText());
            model.setItemsTitle(selectedItems.getItems());

            // The event which is related to this ticket
            model.setEvent(coordinatorModel.getSelectedEvent());

            // Create a ticket
            model.createTicket();

            // TODO: Update coordinator panel with created ticket
//            coordinatorModel.add


            // If everything was fine, show the created ticket
            // New stage for create ticket page
            Stage stage = new Stage();

            // Create new stage for ticket creation and blocking the coordinator dashboard through modality
            // also its passing CoordinatorModel object to CreateTicketController
            WindowUtils.createStage(stage, PageType.TICKET_FRONT_SIDE, this.model, Modality.APPLICATION_MODAL);

            // Window to close
            Stage stageToClose = (Stage) suggestions.getScene().getWindow();

            // Close the stage
            WindowUtils.closeStage(stageToClose);

        } catch (ExceptionHandler ex){
            AlertHandler.displayErrorAlert(ex.getMessage());
        }
    }

    private void checkForNulls() throws ExceptionHandler {
        StringBuilder messageBuilder = new StringBuilder();

        if (firstNameField.getText().isEmpty()) {
            messageBuilder.append(ExceptionMessage.FIRST_NAME_NULL.getValue()).append("\n");
        }

        if (lastNameField.getText().isEmpty()) {
            messageBuilder.append(ExceptionMessage.LAST_NAME_NULL.getValue()).append("\n");
        }

        if (emailAddressField.getText().isEmpty()) {
            messageBuilder.append(ExceptionMessage.EMAIL_NULL.getValue()).append("\n");
        }

        if (phoneNumberLabel.getText().isEmpty()) {
            messageBuilder.append(ExceptionMessage.PHONE_NULL.getValue()).append("\n");
        }

        String message = messageBuilder.toString().trim();
        if (!message.isEmpty()) {
            throw new ExceptionHandler(message);
        }
    }
    @FXML
    private void onClearFieldsButton(ActionEvent actionEvent) {
        // Clear TextFields
        clearFields();
    }

    @FXML
    private void onLastNameField(){

    }


    private void activateAutoCompleteOnFocus(Category category, TextField textField, VBox textFieldVBox){
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            // When focused on TextField
            if (newVal) {
                if (category == Category.ITEM) {
                    setAutoCompleteWithSelectedItems(category, itemNameField, itemsVBox);
                } else {
                    setupAutoComplete(category, textField, textFieldVBox);
                }
            }
        });
    }



    /**
     * General Architecture for autocomplete method
     * It's a vbox which has 2 nodes, first one is a TextField
     * Second is a ListView.
     * By writing a character/sequence in TextField,
     * Program fills the ListView which is right after the TextField.
     * ----------------------
     * | Username: Mic      | <= TextField
     * ----------------------
     * | Michael            |
     * | Michal             | <= ListView
     * | . . .              |
     * ----------------------
     * P.S: This architecture is a bit different for the ticket's extra items, because
     * It needs to show also selected items.
     */
    private void setupAutoComplete(Category category, TextField textField, VBox textFieldVBox){
        if (!textFieldVBox.getChildren().contains(suggestions)) {
            textFieldVBox.getChildren().add(suggestions);
        }
        Scene scene = textField.getScene();
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!suggestions.getBoundsInParent().contains(event.getX(), event.getY())) {
                suggestions.setVisible(false);
                textFieldVBox.getChildren().remove(suggestions);

            }
        });

        autoCompleteListener = (observable, oldValue, newValue) -> {
            try {
                // Checks if category object in the model is same as provided one
                if (model.getCurrentCategory() != category) {
                    model.setCurrentCategory(category);
                }

                // Checks for entered sequence
                model.checkSequence(newValue);

                // Checks if there is no result for input, removes the list view from vbox
                if (model.getMatchedItems().isEmpty()){
                    suggestions.setVisible(false);
                    textFieldVBox.getChildren().remove(suggestions);


                } else {
                    // When we have matching items
                    suggestions.setVisible(true);

                    // Checks if suggestion list view is in vbox, if not, adds it
                    if (!textFieldVBox.getChildren().contains(suggestions)) {
                        textFieldVBox.getChildren().add(suggestions);
                    }

                    // Brings vbox over other nodes
                    textFieldVBox.toFront();

                }

            } catch (ExceptionHandler ex){
                AlertHandler.displayErrorAlert(ex.getMessage());
            }
        };

        textField.textProperty().addListener(autoCompleteListener);

        suggestions.setOnMouseClicked(event -> {
            String selectedItem = suggestions.getSelectionModel().getSelectedItem();
            textField.setText(selectedItem);
            suggestions.setVisible(false);
            textFieldVBox.getChildren().remove(suggestions);
        });

    }


    private void setAutoCompleteWithSelectedItems(Category category, TextField textField, VBox textFieldVBox){


        // Adding selected items to a VBox as first item, because we want to have selected items right away after TextField
        if (!textFieldVBox.getChildren().contains(selectedItems)){
            textFieldVBox.getChildren().add(selectedItems);
        }
        if (!textFieldVBox.getChildren().contains(suggestions)){
            textFieldVBox.getChildren().add(suggestions);
        }

        textField.setOnMouseClicked(event -> {
            selectedItems.setVisible(true);
            selectedItems.setPrefHeight(24 * selectedItems.getItems().size());
        });

        // Brings the vbox contains selected items and suggestion to front
        textFieldVBox.toFront();


        // Closes/hides the selectedItems and suggestions ListViews, if user clicks on anywhere in the scene except on one of them
        Scene scene = textField.getScene();
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (!suggestions.getBoundsInParent().contains(event.getX(), event.getY())) {
                selectedItems.setVisible(false);
                suggestions.setVisible(false);
                itemsVBox.setPrefHeight(itemNameField.getPrefHeight());

//                itemsVBox.getChildren().remove(suggestions);
//                itemsVBox.getChildren().remove(selectedItems);

            }
        });


        autoCompleteListener = (observable, oldValue, newValue) -> {
            try {
                // Checks if Items object in the model is same as provided one
                if (model.getCurrentCategory() != Category.ITEM) {
                    model.setCurrentCategory(Category.ITEM);
                }

                // Checks for the entered sequence
                model.checkSequence(newValue);

                // Checks if there is no result for input, removes the list view from vbox
                if (model.getMatchedItems().isEmpty()){
                    suggestions.setVisible(false);
                    textFieldVBox.getChildren().remove(suggestions);
                    itemsVBox.setPrefHeight(itemNameField.getPrefHeight() + selectedItems.getPrefHeight());



                } else {
                    // When we have matching items
                    suggestions.setVisible(true);
                    selectedItems.setVisible(true);

                    itemsVBox.setPrefHeight(Region.USE_COMPUTED_SIZE);

                    // Checks if suggestion list view is in vbox, if not, adds it
                    if (!textFieldVBox.getChildren().contains(suggestions)) {
                        textFieldVBox.getChildren().add(suggestions);
                    }

                    // Brings vbox over other nodes
                    textFieldVBox.toFront();

                }

            } catch (ExceptionHandler ex){
                AlertHandler.displayErrorAlert(ex.getMessage());
            }
        };

        textField.textProperty().addListener(autoCompleteListener);


        // User clicks on an item to select it
        suggestions.setOnMouseClicked(event -> {
            // Get selected item
            String selectedItem = suggestions.getSelectionModel().getSelectedItem();
            // Add the selected item in to the model, which updates SelectedItems list
            model.addItem(selectedItem);

            // Clear TextField, so user can write another item name
            textField.clear();
            // Remove suggestions ListView from VBox after click
            textFieldVBox.getChildren().remove(suggestions);
            // Remove selectedItems ListView from VBox after click
            textFieldVBox.getChildren().remove(selectedItems);

            // Updates the height of the VBox to match the height of the TextField.
            // Because after removing ListViews, the VBox's height will still be the same as before.
            // So it may overlap with the submit and reset buttons.
            itemsVBox.setPrefHeight(itemNameField.getHeight());

            // Keep focus on the items field, because user might want to add more items
            // Otherwise due to listener behavior, it's going to jump on to next node which has focus traversable on
            textField.requestFocus();

        });

        // User clicks on an item which is already selected, selected item removes from the list
        selectedItems.setOnMouseClicked(event -> {
            String selectedItem = selectedItems.getSelectionModel().getSelectedItem();
            model.removeItem(selectedItem);
            itemsVBox.setPrefHeight(itemNameField.getHeight());
        });


    }

    private void clearFields(){
        model.clearAll();
        firstNameField.clear();
        lastNameField.clear();
        emailAddressField.clear();
        phoneNumberLabel.clear();
        itemNameField.clear();
        suggestions.getItems().clear();
        selectedItems.getItems().clear();
    }

}
