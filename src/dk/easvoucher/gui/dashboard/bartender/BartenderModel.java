package dk.easvoucher.gui.dashboard.bartender;

import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.bll.BartenderLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BartenderModel {

    BartenderLogic logic = new BartenderLogic();

    private final SimpleObjectProperty<Ticket> ticket = new SimpleObjectProperty<>();


    public void setTicket(String uuid) {
        try {
            Ticket t = logic.getTicket(uuid);
            ticket.set(t);
        } catch (ExceptionHandler e) {
            // Handle the exception here, either by logging or taking other appropriate action
            e.printStackTrace(); // or logger.error("Failed to get ticket with uuid: " + uuid, e);
        }
    }

    public Ticket getTicket(){
        return ticket.get();
    }

    public void setTicketInfoBox(ListView<Label> ticketInformation) {
        logic.setTicketInfoBox(ticketInformation, ticket.get());
    }

    public void setExtraItems(ListView<CheckBox> ticketExtraItemsListView) {
        logic.setExtraItems(ticketExtraItemsListView, ticket.get());
    }
}
