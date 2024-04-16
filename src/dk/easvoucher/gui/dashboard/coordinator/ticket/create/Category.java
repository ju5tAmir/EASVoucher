package dk.easvoucher.gui.dashboard.coordinator.ticket.create;

public enum Category {
    FIRST_NAME("first_name"),
    LAST_NAME("last_name"),
    EMAIL("email"),
    PHONE_NUMBER("phone_number"),
    ITEM("item");

    final String value;
    Category(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
