package dk.easvoucher;

import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.AdminDAO;
import dk.easvoucher.utils.WindowUtils;
import dk.easvoucher.utils.PageType;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    // TODO 1: Don't forget to add coordinator creator to EventCoordinators table in database when creating a new Event
    // TODO 2: Make events info somehow to now show horizontal scroll pane.
    // TODO 3: Oh man, CoordinatorController and almost everything related to it fucked up. fix it asap.
    // TODO 4: Implement suggestions on user inputs
    // TODO 5: Implement Bartender panel
    // TODO 6: Fix vbox resizing for items in create ticket controller
    // TODO 7: Do not forget to check comments
    // TODO 8: Do not forget to remove unused imported packages and classes
    // TODO 9: Delete Main2

    @Override
    public void start(Stage primaryStage) throws Exception {

        AdminDAO a = new AdminDAO();
        System.out.println(a.getAllEvents());

        // Create and show the stage for Login page
         WindowUtils.createStage(primaryStage, PageType.LOGIN);

    }

    public static void main(String[] args) {

        Application.launch(args);
    }
}
