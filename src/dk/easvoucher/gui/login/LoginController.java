package dk.easvoucher.gui.login;

import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.utils.PageType;
import dk.easvoucher.utils.WindowUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable, IController  {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private LoginModel loginModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Instantiate a new Model object
        this.loginModel = new LoginModel();
    }

    /**
     * Attempts to authenticate the user using the provided credentials.
     * If successful, opens the employee dashboard based on the user role,
     * otherwise displays an error alert.
     * */
    @FXML
    private void loginButton() throws ExceptionHandler{
        try {
            // Attempts to log in with the provided credentials
            loginModel.authenticate(usernameField.getText(), passwordField.getText());

            // Open the employee dashboard based on user's role in the system
            openEmployeeDashboard();

            // Close the login stage
            closeLoginPage();

        } catch (ExceptionHandler e) {
            // Display an error alert indicating incorrect credentials
            AlertHandler.displayErrorAlert(e.getMessage());
        }

    }

    /**
     *  Create a new dashboard stage for the employee based on role
     */
    private void openEmployeeDashboard() throws ExceptionHandler {
        // Initiate a new stage for the dashboard
        Stage stage = new Stage();

        // Create a new stage and send model object to it, because we need it in user dashboard
        WindowUtils.createStage(stage, PageType.fromString(loginModel.getLoggedInEmployee().getRole().getValue()), loginModel);
    }

    /**
     *  Closes the current login stage
     */
    private void closeLoginPage() throws ExceptionHandler {
        // get the current stage
        Stage stageToClose = (Stage) passwordField.getScene().getWindow();

        // close the current  stage
        WindowUtils.closeStage(stageToClose);
        }

    @Override
    public void setModel(Object model) {

    }
}

