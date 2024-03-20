package dk.easvoucher.be.entities;

public class Ticket {
    private int ticketId;
    private String ticketNumber;
    private String qrCode;
    private String barcode;
    private String UUID;
    private Integer ticketTypeId;
    private Integer eventId;
    private Integer customerId;

    public Ticket(int ticketId, String ticketNumber, String qrCode, String barcode, String UUID, Integer ticketTypeId, Integer eventId, Integer customerId) {
        this.ticketId = ticketId;
        this.ticketNumber = ticketNumber;
        this.qrCode = qrCode;
        this.barcode = barcode;
        this.UUID = UUID;
        this.ticketTypeId = ticketTypeId;
        this.eventId = eventId;
        this.customerId = customerId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Integer getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Integer ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", barcode='" + barcode + '\'' +
                ", UUID='" + UUID + '\'' +
                ", ticketTypeId=" + ticketTypeId +
                ", eventId=" + eventId +
                ", customerId=" + customerId +
                '}';
    }
}