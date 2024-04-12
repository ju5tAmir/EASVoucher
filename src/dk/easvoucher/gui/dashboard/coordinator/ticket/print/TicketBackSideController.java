package dk.easvoucher.gui.dashboard.coordinator.ticket.print;

import dk.easvoucher.exeptions.AlertHandler;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.gui.dashboard.IController;
import dk.easvoucher.gui.dashboard.coordinator.ticket.create.CreateTicketModel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class TicketBackSideController implements IController<CreateTicketModel> {
    @FXML
    private VBox eventNotesVbox, ticketItemsVbox;
    CreateTicketModel createTicketModel;

    TicketSidesModel model;

    public TicketBackSideController(){
        this.model = new TicketSidesModel();
    }


    @Override
    public void setModel(CreateTicketModel createTicketModel) {
        this.createTicketModel = createTicketModel;

        model.setTicket(createTicketModel.getTicket());

        try {
            loadUI();
        } catch (ExceptionHandler ex){
            AlertHandler.displayErrorAlert(ex.getMessage());
        }
    }



    private void loadUI() throws ExceptionHandler {
        // Load event notes box
        VBox newEventNotesVBox = model.getEventNotesVBox();
        eventNotesVbox.getChildren().setAll(newEventNotesVBox.getChildren());

        // Load ticket items box
        VBox vbox = model.getTicketItemsVBox();
        ticketItemsVbox.getChildren().setAll(vbox.getChildren());

    }


}
