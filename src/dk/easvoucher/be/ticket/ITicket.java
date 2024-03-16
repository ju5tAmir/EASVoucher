package dk.easvoucher.be.ticket;

public interface ITicket {
    int getId();
    int getEventId();
    TicketType getType();
}
