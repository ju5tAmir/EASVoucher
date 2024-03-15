package dk.easvoucher.be.response;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.user.IUser;
import dk.easvoucher.be.user.User;
import javafx.collections.ObservableList;

import java.util.List;

public class Response implements IResponse {
    private boolean status;
    private IUser user;
    private List<Event> events;
    private List<ITicket> tickets;

    public Response(){

    }

    public Response(boolean status){
        this.status = status;

    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public int getCode() {
        return 200;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public List<Event> getEvents() {
        return events;
    }

    @Override
    public List<ITicket> getTickets() {
        return tickets;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setTickets(List<ITicket> tickets) {
        this.tickets = tickets;
    }
}
