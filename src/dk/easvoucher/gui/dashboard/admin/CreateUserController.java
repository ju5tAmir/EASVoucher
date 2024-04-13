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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.ui.Model;

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


    /**
     * author: <a href="https://github.com/NilIQW">Nil</a>
     */
    public void saveButton(ActionEvent actionEvent) throws ExceptionHandler, SQLException {

        try {
            model.addUser(usernameField.getText(), passwordField.getText(), roleChoice.getValue());

            AlertHandler.displayInformation(ExceptionMessage.USER_CREATED_SUCCESSFULLY.getValue());
            // Close the window
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } catch (ExceptionHandler exceptionHandler){
            AlertHandler.displayErrorAlert(exceptionHandler.getMessage());
        }
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
