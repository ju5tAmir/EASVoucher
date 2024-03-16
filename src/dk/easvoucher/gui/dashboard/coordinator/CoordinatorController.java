package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CoordinatorController implements IController {
    private Model model;
    @FXML
    private Label customLabel;
    @Override
    public void setModel(Model model) {
        this.model = model;
        customLabel.setText(model.getUser().getRole().getValue());
    }
}
