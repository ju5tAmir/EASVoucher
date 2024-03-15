package dk.easvoucher.gui.dashboard.admin;

import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController implements IController {
    @FXML
    private Label usernameLabel;

    private Model model;

    @Override
    public void setModel(Model model){
        this.model = model;

        usernameLabel.setText(model.getUser().getUsername());
    }

}
