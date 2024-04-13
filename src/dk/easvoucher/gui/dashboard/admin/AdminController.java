package dk.easvoucher.gui.dashboard.admin;

import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.login.LoginModel;
import dk.easvoucher.utils.PageType;
import dk.easvoucher.utils.WindowUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 * author: <a href="https://github.com/NilIQW">Nil</a>
 */
public class AdminController implements IController<LoginModel> {
    @FXML
    private Label usernameLabel;
    @FXML
    private TableView<Employee> userTableview;

    @FXML
    private TableColumn<Employee, String> idColumn;
    @FXML
    private TableColumn<Employee, String> usernameColumn;
    @FXML
    private TableColumn<Employee, UserRole> roleColumn;
    private AdminModel model;

    @Override
    public void setModel(LoginModel loginModel){
        try {
            this.model = new AdminModel();

            model.setAdmin(loginModel.getLoggedInEmployee());

            initializeUserTable();

            initializeColumns();
        } catch (ExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(ActionEvent actionEvent) throws ExceptionHandler {

        Stage stage = new Stage();

        WindowUtils.createStage(stage, PageType.CREATE_USER, model, Modality.WINDOW_MODAL);

    }
    public void deleteUser(ActionEvent actionEvent) throws ExceptionHandler {
        try {
        Employee selectedUser = userTableview.getSelectionModel().getSelectedItem();
        model.removeUser(selectedUser);
        initializeUserTable();
        AlertHandler.displayInformation(ExceptionMessage.USER_REMOVED_SUCCESSFULLY.getValue());
        } catch (ExceptionHandler ex){
            AlertHandler.displayErrorAlert(ex.getMessage());
        }

    }
    public void deleteEvent(ActionEvent actionEvent) {


    }

    public void initializeUserTable() throws ExceptionHandler{
        userTableview.setItems(model.getEmployees());
    }
    public void initializeColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

}
