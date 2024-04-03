package dk.easvoucher.be.ticket;


public interface ITicket {
    int getId();
    String getQrCode();
    String getBarcode();
    int getTypeId(); // Adjusted from TicketType getType() to match the database schema
    int getEventId();
}
