package dk.easvoucher.be.ticket;


public class Ticket implements ITicket{
    private int id;
    private int eventId;
    private TicketType type;


    public Ticket(int id, int eventId, TicketType type) {
        this.id = id;
        this.eventId = eventId;
        this.type = type;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getEventId() {
        return 0;
    }

    @Override
    public TicketType getType() {
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setType(TicketType type) {
        this.type = type;
    }
}
