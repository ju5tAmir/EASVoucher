package dk.easvoucher.gui.dashboard.bartender;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.login.LoginModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

public class BartenderController implements IController<LoginModel>, Initializable, HttpHandler {

    @FXML
    private Label statusLabel, generalInfoLabel, ticketItemsLabel;
    @FXML
    private ListView<Label> ticketInformation;
    @FXML
    private ListView<CheckBox> ticketExtraItemsListView;
    @FXML
    private Button deleteButton, updateButton, newButton;

    private LoginModel loginModel;

    private HttpServer httpServer;
    private String ngrokUrl;

    private BartenderModel model;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Start Ngrok server
            NgrokServer.main(new String[]{});

            // Get Ngrok URL
            ngrokUrl = NgrokServer.getPublicUrl().replace(":8080", ":8081");

            // Create HTTP server
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/", this);

            httpServer.setExecutor(Executors.newCachedThreadPool());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Extract the URL path
        String uuid = exchange.getRequestURI().getPath();
        System.out.println("Received request for path: " + uuid);


        Platform.runLater(() -> {
            statusLabel.setText("Ticket retrieved successfully.");

            model.setTicket(uuid.substring(1));
            model.setTicketInfoBox(ticketInformation);
            model.setExtraItems(ticketExtraItemsListView);

            // set visible
            generalInfoLabel.setVisible(true);
            ticketItemsLabel.setVisible(true);
            ticketInformation.setVisible(true);
            ticketExtraItemsListView.setVisible(true);
            deleteButton.setVisible(true);
            updateButton.setVisible(true);
            newButton.setVisible(true);

        });
    }


    public void onScanATicket(ActionEvent actionEvent) {
        try {

            // Start HTTP server
            httpServer.start();

            System.out.println("Ngrok URL: " + ngrokUrl);
            System.out.println("Listening on port 8080...");

            Platform.runLater(() -> {
                statusLabel.setText("Waiting for ticket to be scanned ...");
            });

            URL url = new URL("http://164.90.238.69:5000/seturl");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String postData = "value=" + ngrokUrl;
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            System.out.println(conn.getResponseMessage());
            System.out.println(conn.getResponseCode());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setModel(LoginModel loginModel) {
        this.loginModel = loginModel;

        this.model = new BartenderModel();
    }
}
