package dk.easvoucher.gui.coordinator;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainCoordinatorWindowController {

    public void eventWindow(ActionEvent event) {
        openWindow("EventWindow.fxml", "Event Window");
    }

    public void ticketWindow(ActionEvent event) {
        openWindow("TicketWindow.fxml", "Ticket Window");
    }

    public void accessWindow(ActionEvent event) {
        openWindow("AccessWindow.fxml", "Access Window");
    }

    private void openWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

