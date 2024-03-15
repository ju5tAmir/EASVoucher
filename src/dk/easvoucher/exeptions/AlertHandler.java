package dk.easvoucher.exeptions;

import javafx.scene.control.Alert;

public class AlertHandler {


    /**
     * Display an error alert
     * @param message Message contains details about the error
     */
    public static void displayErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }



}
