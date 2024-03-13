package dk.easvoucher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initiate new FXMLLoader with login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/login/LoginView.fxml"));
        // Initiate new Scene based on loaded FXML file
        Scene scene = new Scene(loader.load());
        // Set loaded scene into stage
        primaryStage.setScene(scene);
        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
