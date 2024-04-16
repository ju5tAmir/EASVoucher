package dk.easvoucher.be.ticket;

import dk.easvoucher.be.event.Event;

import java.util.UUID;

public class SuperTicket implements ITicket{
    private int id;
    private Event event;
    private Item item;
    private UUID uuid;
    private boolean isClaimed;
    private TicketType ticketType;

    public SuperTicket(int id, Event event, Item item, UUID uuid, boolean isClaimed, TicketType ticketType) {
        this.id = id;
        this.event = event;
        this.item = item;
        this.uuid = uuid;
        this.isClaimed = isClaimed;
        this.ticketType = ticketType;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isClaimed() {
        return isClaimed;
    }

    public void setClaimed(boolean claimed) {
        isClaimed = claimed;
    }
}
