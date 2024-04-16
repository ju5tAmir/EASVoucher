package dk.easvoucher.gui.dashboard.admin;


import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import dk.easvoucher.gui.dashboard.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable, IController<AdminModel> {
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<UserRole> roleChoice;
    @FXML
    private TextField usernameField;
    private AdminModel model;
    private Employee selectedUser;
    @FXML
    private TableView<Employee> userTableview;
    public void setUserTableView(TableView<Employee> userTableview) {
        this.userTableview = userTableview;
    }

    private void refreshUserTableView() throws ExceptionHandler {
        if (userTableview != null) {
            userTableview.getItems().clear();
            userTableview.getItems().addAll(model.getEmployees());
        }
    }

    public void setSelectedUser(Employee selectedUser) {
        this.selectedUser = selectedUser;
        usernameField.setText(selectedUser.getUsername());
        passwordField.setText("");
        roleChoice.setValue(selectedUser.getRole());
    }

    /**
     * author: <a href="https://github.com/NilIQW">Nil</a>
     */
    public void saveButton(ActionEvent actionEvent) throws ExceptionHandler, SQLException {
        // Check if selectedUser is null or not
        if (selectedUser != null) {
            try {
                // Update the selected user with new information
                String newPassword = passwordField.getText();
                selectedUser.setUsername(usernameField.getText());
                selectedUser.setRole(roleChoice.getValue());
                model.updateUser(selectedUser, newPassword);

                AlertHandler.displayInformation(ExceptionMessage.USER_UPDATED_SUCCESSFULLY.getValue());

                userTableview.refresh();
                // Close the window
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } catch (ExceptionHandler exceptionHandler) {
                AlertHandler.displayErrorAlert(exceptionHandler.getMessage());
            }
        } else {
            model.addUser(usernameField.getText(), passwordField.getText(), roleChoice.getValue());
            AlertHandler.displayInformation(ExceptionMessage.USER_CREATED_SUCCESSFULLY.getValue());
        }

        // Close the window
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateChoiceBox();
    }

    private void populateChoiceBox() {
        // Get enum values
        UserRole[] roles = UserRole.values();

        // Convert enum values to ObservableList
        ObservableList<UserRole> roleList = FXCollections.observableArrayList(roles);

        // Populate ChoiceBox with enum values
        roleChoice.setItems(roleList);
    }

    @Override
    public void setModel(AdminModel model) {
        this.model = model;

    }
}
