
package dk.easvoucher.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dk.easvoucher.utils.DashboardType; // Import PageType enum


import java.io.IOException;

public class WindowUtils {
    public static void CreateStage(Stage stage, DashboardType dashboardType) throws IOException {
        switch (dashboardType){
            case LOGIN:
                // Initiate new FXMLLoader with login page
                FXMLLoader loader = new FXMLLoader(WindowUtils.class.getResource("../gui/login/LoginView.fxml"));
                // Initiate new Scene based on loaded FXML file
                Scene scene = new Scene(loader.load());
                // Set loaded scene into stage
                stage.setScene(scene);
                // Show the stage
                stage.show();
                break;

        }

    }
}
