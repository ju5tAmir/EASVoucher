package dk.easvoucher.be.user;



public enum UserRole {
    ADMIN("admin"),
    COORDINATOR("coordinator"),

    BARTENDER("bartender");

    private final String value;
    public String getValue(){
        return this.value;
    }
    UserRole(String value){
        this.value = value;
    }

    // Method to get enum value by string
    public static UserRole fromString(String text) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.value.equalsIgnoreCase(text)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
