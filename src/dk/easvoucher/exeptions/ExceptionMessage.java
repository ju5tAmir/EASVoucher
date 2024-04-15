package dk.easvoucher.exeptions;

public enum ExceptionMessage {
    DB_CONNECTION_FAILURE("An error occurred while trying to connect to the database"),
    USER_NOT_FOUND("The entered username does not exist"),
    INCORRECT_CREDENTIALS("The username or password you entered is not correct."),
    COORDINATOR_NOT_FOUND("The requested coordinator does not exists."),
    ILLEGAL_FILE("Illegal file operation."),
    FXML_LOAD_ERROR("Error loading FXML file."),
    INVALID_SUGGESTION_PARAMETER("You entered suggestion type is invalid."),
    NO_EVENT_SELECTED("You need to select an event, in order to create a ticket."),
    INSERTION_FAILED("There is a problem with inserting data into the database."),
    CUSTOMER_INSERTION_FAILED("Inserting customer into the database has failed."),
    KEY_GENERATION_FAILURE("No keys were generated."),
    TICKET_INSERTION_FAILED("Inserting ticket into the database has failed."),
    ITEM_INSERTION_FAILED("Inserting ticket items into the database has failed."),
    FIRST_NAME_NULL("First name value cannot be null or empty"),
    LAST_NAME_NULL("Last name value cannot be null or empty"),
    EMAIL_NULL("Email address value cannot be null or empty"),
    PHONE_NULL("Phone number value cannot be null or empty"),
    SELECT_A_TICKET("Please select the ticket you want to delete."),
    SELECT_AN_EVENT("Please select the event you want to delete."),
    INCORRECT_PASSWORD("The password you entered is incorrect."),
    ERROR_IN_CREATING_USER("An error occured while creating new user."),
    ERROR_IN_REMOVING_USER("An error occured while removing the user."),
    ERROR_IN_REMOVING_EVENT("An error occured while removing the event."),
    USER_CREATED_SUCCESSFULLY("User created successfully."),
    USER_REMOVED_SUCCESSFULLY("User removed successfully"),
    USER_UPDATED_SUCCESSFULLY("User updated successfully"),
    ERROR_IN_UPDATING_USER("An error occured while editing the user");



    private final String value;

    public String getValue() {
        return value;
    }

    ExceptionMessage(String value){
        this.value = value;
    }
}
