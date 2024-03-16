package dk.easvoucher.be.response;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.user.IUser;
import dk.easvoucher.be.user.User;
import javafx.collections.ObservableList;

import java.util.List;

public interface IResponse {
    boolean getStatus();
    int getCode();
    IUser getUser();
    List<Event> getEvents();
    List<ITicket> getTickets();

}
