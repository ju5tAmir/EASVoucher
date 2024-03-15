package dk.easvoucher.gui.login;

import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.model.Model;
import dk.easvoucher.utils.PageType;
import dk.easvoucher.utils.WindowUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    // Username TextField
    @FXML
    private TextField usernameField;
    // Password PasswordField
    @FXML
    private PasswordField passwordField;
    // Represents the model component of the login page.
    private Model model;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Instantiate a new Model object
        this.model = new Model();
    }

    /**
     * on submit button press
     * */
    @FXML
    private void submitButton() throws IOException, ExceptionHandler {
        // Login with the entered credentional
        model.login(usernameField.getText(), passwordField.getText());

        // Checks if the user logged in successfully
        if (model.getLoginStatus().get()){
            // Initiate new stage for new page
            Stage stage = new Stage();

            // Create a new stage based on user role
            WindowUtils.createStage(stage, PageType.fromString(model.getUser().getRole().getValue()), model);

            // Close the current stage
            Stage stageToClose = (Stage) this.passwordField.getScene().getWindow();
            WindowUtils.closeStage(stageToClose);

        }

    }
}