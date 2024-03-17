package dk.easvoucher.gui.dashboard.admin;

import dk.easvoucher.be.user.User;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.model.Model;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private ChoiceBox<UserRole> roleChoice;
    @FXML
    private TextField usernameField;
    private Model model;
    private AdminController adminController; // Add a field for AdminController
    private User selectedUser; // Add field to hold selected user

    // Setter method for selected user
    public void setUser(User user) {
        this.selectedUser = user;
        autofillFields();
    }

    private void autofillFields() {
        if (selectedUser != null) {
            usernameField.setText(selectedUser.getUsername());
           roleChoice.setValue(selectedUser.getRole());
        }
    }

    public void setModel(Model model) {
        this.model = model;
    }

    // Setter method for AdminController
    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }
    public void saveButton(ActionEvent actionEvent) throws ExceptionHandler, SQLException {
        if (selectedUser != null) {
            // Update existing user
            String newUser = usernameField.getText();
            String newPassword = passwordField.getHideCharacter();
            selectedUser.setUsername(newUser);
            selectedUser.setPassword(newPassword);
            selectedUser.setRole(roleChoice.getValue());
            model.updateUser(selectedUser);
        } else {
            // Create new user
            String user = usernameField.getText();
            String password = passwordField.getHideCharacter();
            User newUser = new User(user, password, roleChoice.getValue());
            model.createUser(newUser);
        }

        // Call the method to update TableView in AdminController
        if (adminController != null) {
            adminController.initializeUserTable();
        } else {
            System.out.println("adminController null");
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
}
