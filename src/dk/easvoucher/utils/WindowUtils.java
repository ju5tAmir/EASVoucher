package dk.easvoucher.utils;

import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowUtils {
    private static FXMLLoader loader;


    /**
     * Create and show a new stage
     * @param stage stage to create
     * @param pageType FXML file that needs to be loaded on the scene of the stage
     */
    public static void createStage(Stage stage, PageType pageType) throws IOException {

        switch (pageType) {
            case LOGIN:
                // Initiate new FXMLLoader with login page
                loader = new FXMLLoader(WindowUtils.class.getResource("../gui/login/LoginView.fxml"));
                break;

            case ADMIN_DASHBOARD:
                // Initiate new FXMLLoader with admin dashboard
                loader = new FXMLLoader(WindowUtils.class.getResource("../gui/dashboard/admin/AdminView.fxml"));
                break;

            case COORDINATOR_DASHBOARD:
                // Initiate new FXMLLoader with coordinator dashboard
                loader = new FXMLLoader(WindowUtils.class.getResource("../gui/dashboard/coordinator/CoordinatorView.fxml"));
                break;

            default:
            break;
        }
        // Initiate new Scene based on loaded FXML file
        Scene scene = new Scene(loader.load());
        // Set loaded scene into stage
        stage.setScene(scene);
        // Show the stage
        stage.show();
    }





    public static void createStage(Stage stage, PageType pageType, Model model) throws IOException {

        switch (pageType) {
            case LOGIN:
                // Initiate new FXMLLoader with login page
                loader = new FXMLLoader(WindowUtils.class.getResource("../gui/login/LoginView.fxml"));
                break;

            case ADMIN_DASHBOARD:
                // Initiate new FXMLLoader with admin dashboard
                loader = new FXMLLoader(WindowUtils.class.getResource("../gui/dashboard/admin/AdminView.fxml"));
                break;

            case COORDINATOR_DASHBOARD:
                // Initiate new FXMLLoader with coordinator dashboard
                loader = new FXMLLoader(WindowUtils.class.getResource("../gui/dashboard/coordinator/CoordinatorView.fxml"));
                break;

            default:
                break;
        }
        // Initiate new Scene based on loaded FXML file
        Scene scene = new Scene(loader.load());
        // Set loaded scene into stage
        stage.setScene(scene);
        // Show the stage
        stage.show();

        // Send model to controller class of the user (can be admin and coordinator)
        IController userController = loader.getController();
        userController.setModel(model);

    }


    /**
     * Close a stage
     * @param stage stage to close
     */
    public static void closeStage(Stage stage){
        stage.close();
    }

}
