package dk.easvoucher.gui.dashboard.admin;

import dk.easvoucher.be.user.User;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements IController {
    @FXML
    private Label usernameLabel;
    @FXML
    private TableView<User> userTableview;
    private Model model;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, UserRole> roleColumn;


    @Override
    public void setModel(Model model){
        this.model = model;

        usernameLabel.setText(model.getUser().getUsername());
        initializeUserTable();
        initializeColumns();

    }

    public void createUser(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            CreateUserController controller = loader.getController();

            // Set the model instance to the controller
            controller.setModel(model);
            controller.setAdminController(this); // Set the AdminController instance

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public void deleteUser(ActionEvent actionEvent) throws ExceptionHandler, SQLException {
        User selectedUser = userTableview.getSelectionModel().getSelectedItem();
        model.removeUser(selectedUser.getId());
        initializeUserTable();
    }
    public void deleteEvent(ActionEvent actionEvent) {


    }

    public void initializeUserTable() {
        try {
            List<User> userList = model.getAllUsers();
            ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
            userTableview.setItems(observableUserList);
        } catch (SQLException | ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
    public void initializeColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));
    }

    public void editUser(ActionEvent actionEvent) {
        User selectedUser = userTableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
                Parent root = loader.load();

                // Get the controller instance
                CreateUserController controller = loader.getController();

                // Set the model instance to the controller
                controller.setModel(model);
                controller.setAdminController(this); // Set the AdminController instance

                // Set the selected user to autofill the fields
                controller.setUser(selectedUser);

                // Create a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
