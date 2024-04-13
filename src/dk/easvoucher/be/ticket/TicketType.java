package dk.easvoucher.be.ticket;


public enum TicketType {
    STANDARD("standard"),
    SUPER("super");
    private final String value;

    public String getValue(){
        return value;
    }

    TicketType(String value){
        this.value = value;
    }

}
