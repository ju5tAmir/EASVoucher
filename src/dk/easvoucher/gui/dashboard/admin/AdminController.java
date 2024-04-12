package dk.easvoucher.gui.dashboard.admin;

import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.login.LoginModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController implements IController<LoginModel> {
    @FXML
    private Label usernameLabel;
    private LoginModel loginModel;

    @Override
    public void setModel(LoginModel loginModel){
        this.loginModel = loginModel;
        usernameLabel.setText("Welcome " + loginModel.getLoggedInEmployee().getUsername());
    }



}
