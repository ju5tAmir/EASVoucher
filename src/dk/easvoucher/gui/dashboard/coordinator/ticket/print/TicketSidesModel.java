package dk.easvoucher.gui.dashboard.coordinator.ticket.print;

import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.bll.TicketSidesLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class TicketSidesModel {
    private final SimpleObjectProperty<Ticket> ticket = new SimpleObjectProperty<>();
    private final ObservableList<Label> eventNotes = FXCollections.observableArrayList();
    TicketSidesLogic logic = new TicketSidesLogic();
    public void setTicket(Ticket ticket) {
        this.ticket.set(ticket);
    }

    private Ticket getTicket() {
        return ticket.get();
    }

    public ObservableList<Label> getEventNotes() throws ExceptionHandler {

        List<Label> labels = logic.getNoteLabelForEvent(ticket.get().getEvent());
        eventNotes.setAll(labels);
        System.out.println(eventNotes.size());
        return eventNotes;
    }

    public VBox getEventNotesVBox() {
        return logic.getEventNotesVBox(ticket.get().getEvent());
    }

    public VBox getTicketItemsVBox() {
        return logic.getTicketItemsVBox(ticket.get());
    }
}
