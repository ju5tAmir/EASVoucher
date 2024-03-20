package dk.easvoucher.gui.dashboard.coordinator;

import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CoordinatorController implements IController {
    private Model model;
    @FXML
    private Label customLabel;
    @Override
    public void setModel(Model model) {
        this.model = model;
        customLabel.setText(model.getUser().getRole().getValue());
    }

    public void eventButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easvoucher/gui/dashboard/coordinator/EventWindow.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Event Window");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ticketButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easvoucher/gui/dashboard/coordinator/TicketWindow.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ticket Window");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accessButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dk/easvoucher/gui/dashboard/coordinator/AccessWindow.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Access Window");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}