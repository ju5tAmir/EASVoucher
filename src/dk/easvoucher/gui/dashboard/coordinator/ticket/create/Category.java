package dk.easvoucher.gui.dashboard.coordinator.ticket.create;

public enum Suggestion {
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    EMAIL("email"),
    PHONE_NUMBER("phone_number");

    final String value;
    Suggestion(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
