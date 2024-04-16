package dk.easvoucher.be.ticket;

import dk.easvoucher.be.event.Event;

import java.util.UUID;

public interface ITicket {

    int getId();
    void setId(int id);

    Event getEvent();
    void setEvent(Event event);


    TicketType getTicketType();
    void setTicketType(TicketType ticketType);

    UUID getUUID();
    void setUUID(UUID uuid);
}
