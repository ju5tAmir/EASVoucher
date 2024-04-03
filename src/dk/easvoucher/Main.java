package dk.easvoucher;

import dk.easvoucher.dal.LoginHandler;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.utils.WindowUtils;
import dk.easvoucher.utils.PageType;
import javafx.application.Application;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create and show the stage for Login page
        WindowUtils.createStage(primaryStage, PageType.LOGIN);

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
