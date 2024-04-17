package dk.easvoucher.be.ticket;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.Customer;

import java.util.List;
import java.util.UUID;

public class Ticket implements ITicket{
    private int id;
    private Customer customer;
    private Event event;
    private TicketType ticketType;
    private UUID uuid;

    private List<Item> items;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int ticketId) {
        this.id = ticketId;
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public TicketType getTicketType() {
        return ticketType;
    }

    @Override
    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", customer=" + customer +
                ", event=" + event +
                ", ticketType=" + ticketType +
                ", uuid=" + uuid +
                ", items=" + items +
                '}';
    }
}
