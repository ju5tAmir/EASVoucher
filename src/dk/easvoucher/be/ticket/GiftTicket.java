package dk.easvoucher.be.ticket;

public class GiftTicket implements ITicket{

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

    @Override
    public int getCustomerId() {
        return 0;
    }
}
