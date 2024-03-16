package dk.easvoucher.exeptions;

public enum ExceptionMessage {
    DB_CONNECTION_FAILURE("An error occurred while trying to connect to the database"),
    USER_NOT_FOUND("The entered username does not exist"),
    INCORRECT_PASSWORD("The password you entered is incorrect");


    private final String value;

    public String getValue() {
        return value;
    }

    ExceptionMessage(String value){
        this.value = value;
    }
}
