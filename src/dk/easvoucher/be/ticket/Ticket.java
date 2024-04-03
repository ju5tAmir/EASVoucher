package dk.easvoucher.be.ticket;


public class Ticket implements ITicket{
    private int id;
    private String qrCode;
    private String barcode;
    private int typeId;
    private int eventId;

    public Ticket(int id, String qrCode, String barcode, int typeId, int eventId) {
        this.id = id;
        this.qrCode = qrCode;
        this.barcode = barcode;
        this.typeId = typeId;
        this.eventId = eventId;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getQrCode() {
        return null;
    }

    @Override
    public String getBarcode() {
        return null;
    }

    @Override
    public int getTypeId() {
        return 0;
    }

    @Override
    public int getEventId() {
        return 0;
    }
}
