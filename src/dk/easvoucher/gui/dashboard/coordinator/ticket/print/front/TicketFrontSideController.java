package dk.easvoucher.gui.dashboard.coordinator.ticket.print.front;

import dk.easvoucher.bll.TicketSidesLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.dashboard.coordinator.ticket.create.CreateTicketModel;
import dk.easvoucher.utils.DateTimeUtils;
import dk.easvoucher.utils.PageType;
import dk.easvoucher.utils.WindowUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketFrontSideController implements Initializable, IController<CreateTicketModel> {

    @FXML
    private ImageView qrCodeImage, barcodeImage;
    @FXML
    private Label titleLabel, addressLabel, startDateLabel, startTimeLabel, endTimeLabel;
    @FXML
    private VBox eventNotesVbox;

    TicketSidesLogic logic = new TicketSidesLogic();
    CreateTicketModel model;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    private void generateQR(String text){

        Image image = logic.createQRCode(text);
        qrCodeImage.setImage(image);
    }

    private void generateBarcode(String text) throws BarcodeException, OutputException {
        Image image = logic.createBarCode(text);
        barcodeImage.setImage(image);
    }

    @Override
    public void setModel(CreateTicketModel model) {
        this.model = model;
        try {
            setFrontSide();
        } catch (BarcodeException | OutputException e) {
            throw new RuntimeException(e);
        }
    }


    private void setFrontSide() throws OutputException, BarcodeException {
        generateQR(model.getTicket().getUUID().toString());
        generateBarcode(model.getTicket().getUUID().toString());
        titleLabel.setText(model.getTicket().getEvent().getName());
        addressLabel.setText(model.getTicket().getEvent().getLocation());
        startDateLabel.setText(DateTimeUtils.dateToDanishFormat(model.getTicket().getEvent().getStartDate()));
        startTimeLabel.setText(model.getTicket().getEvent().getStartTime().toString().substring(0, 5));
        endTimeLabel.setText(model.getTicket().getEvent().getEndTime().toString().substring(0, 5));

        titleLabel.getScene().setOnMouseClicked(event -> {
            try {
                loadAndShowBackSide();
            } catch (IOException | ExceptionHandler e) {
                throw new RuntimeException(e);
            }
        });
    }



    private void loadAndShowBackSide() throws IOException, ExceptionHandler {
        // New stage for create ticket page
        Stage stage = new Stage();

        // Create new stage for ticket creation and blocking the coordinator dashboard through modality
        // also its passing CoordinatorModel object to CreateTicketController
        WindowUtils.createStage(stage, PageType.TICKET_BACK_SIDE, this.model, Modality.APPLICATION_MODAL);
    }
}



