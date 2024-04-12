package dk.easvoucher.utils;

import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import dk.easvoucher.gui.dashboard.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowUtils {

    /**
     * Load FXML file and return FXMLLoader instance
     */
    private static FXMLLoader loadFXML(String resourcePath) throws IOException {
        return new FXMLLoader(WindowUtils.class.getResource(resourcePath));
    }

    /**
     * Create and show a new stage
     *
     * @param stage     stage to create
     * @param pageType  type of the page to load
     * @param model     optional model to pass to the controller
     * @param modality modality of the stage
     */
    public static <T> void createStage(Stage stage, PageType pageType, T model, Modality modality) throws ExceptionHandler {
        try {
            // Load the FXML file related to requested page type.
            FXMLLoader loader = loadFXML(getResourcePath(pageType));

            // Creates new Scene object based on loaded FXML file.
            Scene scene = new Scene(loader.load());

            // Sets the scene to the previously created stage
            stage.setScene(scene);

            // Set modality for the stage if requested (which means it's not null)
            if (modality != null) stage.initModality(modality);

            // Lock resizing for the all windows
            stage.setResizable(false);

            // Displays the stage
            stage.show();

            // This line retrieves the controller associated with the loaded FXML file.
            // The generic type <T> represents the type of the model provided. By using a generic type,
            // we can accommodate different types of models such as LoginModel, CoordinatorModel, etc.,
            // without hardcoding them manually for each case.
            IController<T> pageController = loader.getController();

            // Set the provided model to the controller.
            // This enables the controller to interact with and manipulate data specific to the provided model type.
            pageController.setModel(model);

        } catch (IOException | ExceptionHandler ex) {
            throw new ExceptionHandler(
                    ExceptionMessage.FXML_LOAD_ERROR.getValue() +
                            "\n" +
                            "Page: " + pageType + // Specify which page causes the error
                            "\n" +
                            "Exception details: " + ex.getMessage() // Include exception details
            );
        }
    }

    /**
     * Overloaded method for creating stage without model and modality
     */
    public static void createStage(Stage stage, PageType pageType) throws ExceptionHandler{
        createStage(stage, pageType, null, null);
    }

    /**
     * Overloaded method for creating stage without model
     */
    public static void createStage(Stage stage, PageType pageType, Modality modality) throws ExceptionHandler {
        createStage(stage, pageType, null, modality);
    }

    /**
     * Overloaded method for creating stage without modality
     */
    public static <T> void createStage(Stage stage, PageType pageType, T model) throws ExceptionHandler {
        createStage(stage, pageType, model, null);
    }

    /**
     * Close a stage
     *
     * @param stage stage to close
     */
    public static void closeStage(Stage stage) {
        stage.close();
    }

    /**
     * Get the resource path for the given PageType
     */
    private static String getResourcePath(PageType pageType) throws ExceptionHandler {
        // Determine the fxml file path based on the specified page type
        return switch (pageType) {
            case LOGIN -> "../gui/login/LoginView.fxml";
            case ADMIN_DASHBOARD -> "../gui/dashboard/admin/AdminView.fxml";
            case COORDINATOR_DASHBOARD -> "../gui/dashboard/coordinator/CoordinatorView.fxml";
            case BARTENDER_DASHBOARD -> "../gui/dashboard/bartender/BartenderView.fxml";
            case CREATE_TICKET -> "../gui/dashboard/coordinator/ticket/create/CreateTicket.fxml";
            case TICKET_FRONT_SIDE -> "../gui/dashboard/coordinator/ticket/print/front/Ticket-Frontside.fxml";
            case TICKET_BACK_SIDE -> "../gui/dashboard/coordinator/ticket/print/Ticket-Backside.fxml";

            // Throws an illegal file exception if none of the files matches to the requested page type
            default -> throw new ExceptionHandler(ExceptionMessage.ILLEGAL_FILE.getValue());
        };
    }
}
