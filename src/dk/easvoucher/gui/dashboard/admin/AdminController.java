package dk.easvoucher.gui.dashboard.admin;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.login.LoginModel;

import dk.easvoucher.utils.PageType;
import dk.easvoucher.utils.WindowUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


/**
 * Author: Nilofar (<a href="https://github.com/NilIQW">...</a>)
 */
public class AdminController implements IController<LoginModel> {
    @FXML
    private TableView<Employee> userTableview;
    private AdminModel model;
    @FXML
    private TableColumn<Employee, String> usernameColumn;
    @FXML
    private TableColumn<Employee, UserRole> roleColumn;
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn timeColumn;
    @FXML
    private TableColumn locationColumn;


    @Override
    public void setModel(LoginModel loginModel){
        this.model = new AdminModel();
        this.model.setAdmin(loginModel.getLoggedInEmployee());

        initializeUserTable();
        initializeColumns();
        initializeEventsTableColumns();
        initializeEventsTable();
    }

    private void initializeEventsTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
    }
    public void initializeEventsTable() {
        try {
            List<Event> eventsList = model.getAllEvents();
            ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventsList);
            eventsTable.setItems(observableEventList);
        }
        catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(ActionEvent actionEvent) throws ExceptionHandler {
        // Create a new stage
        Stage stage = new Stage();

        WindowUtils.createStage(stage, PageType.CREATE_USER, model, Modality.WINDOW_MODAL);

    }
    public void editUser(ActionEvent actionEvent) throws IOException {
        Employee selectedUser = userTableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            //Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            CreateUserController controller = loader.getController();
            controller.setUserTableView(userTableview);

            // Set the model instance to the controller
            controller.setModel(model);

            // Set the selected user to autofill the fields
            controller.setSelectedUser(selectedUser);

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    public void deleteUser(ActionEvent actionEvent) throws ExceptionHandler, SQLException {
        Employee selectedUser = userTableview.getSelectionModel().getSelectedItem();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText("Confirm Deletion");
        confirmationAlert.setContentText("Are you sure you want to delete this user?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            model.removeUser(selectedUser);
        }
    }
    public void deleteEvent(ActionEvent actionEvent) throws SQLException {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText("Confirm Deletion");
        confirmationAlert.setContentText("Are you sure you want to delete this event?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            model.deleteEvent(selectedEvent);
            initializeEventsTable();
        } catch (ExceptionHandler e){
            AlertHandler.displayErrorAlert(e.getMessage());
        }
        }
    }

    public void initializeUserTable() {
        try {
            userTableview.setItems(model.getEmployees());
        } catch (ExceptionHandler e) {
            AlertHandler.displayErrorAlert(e.getMessage());
        }
    }
    public void initializeColumns(){
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }
    public void logOut(ActionEvent actionEvent) throws IOException, ExceptionHandler {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        WindowUtils.createStage(primaryStage, PageType.LOGIN);
    }

}
