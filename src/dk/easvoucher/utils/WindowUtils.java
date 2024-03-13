package dk.easvoucher.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateStage {
    public static void CreateStage(Stage stage, PageType pageType) throws IOException {
        switch (pageType){
            case LOGIN:
                // Initiate new FXMLLoader with login page
                FXMLLoader loader = new FXMLLoader(CreateStage.class.getResource("../gui/login/LoginView.fxml"));
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
