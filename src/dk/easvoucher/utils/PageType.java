package dk.easvoucher.utils;

public enum PageType {

    LOGIN("login"),
    ADMIN_DASHBOARD("admin"),
    COORDINATOR_DASHBOARD("coordinator"),

    CREATE_USER("user");

    private final String value;

    public String getValue(){
        return value;
    }

    PageType(String value){
        this.value = value;
    }


    // Method to get enum value by string
    public static PageType fromString(String text) {
        for (PageType pageType : PageType.values()) {
            if (pageType.value.equalsIgnoreCase(text)) {
                return pageType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
