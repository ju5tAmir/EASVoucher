package dk.easvoucher.gui.dashboard.coordinator.ticket.create;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.Item;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.user.Customer;
import dk.easvoucher.bll.CreateTicketLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketModel {

    // ToDo: Complete suggestions implementation
    // This observable list contains all the words for text suggestion
    //  It can contain customers full name, events name.
    private final ObservableList<String> allAvailableWords = FXCollections.observableArrayList();

    // This observable list contains all the matched words with user input for that category
    // It can contain customers full name, events name.
    private final ObservableList<String> matchedItems = FXCollections.observableArrayList();

    private final SimpleObjectProperty<Category> currentCategory = new SimpleObjectProperty<>();

    // List to store selected items
    private final ObservableList<String> selectedItems = FXCollections.observableArrayList();

    // Ticket object
    private final SimpleObjectProperty<Ticket> ticket = new SimpleObjectProperty<>();

    // Event object which ticket is creating for it
    private final SimpleObjectProperty<Event> event = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Customer> customer = new SimpleObjectProperty<Customer>();
    private final CreateTicketLogic logic = new CreateTicketLogic();

    public CreateTicketModel() throws ExceptionHandler {
        // Instantiate objects for customer, ticket and event
        ticket.set(new Ticket());
//        event.set(new Event());
        customer.set(new Customer());

        // Update event object for ticket
//        ticket.get().setEvent(event.get());
        // Update customer object for ticket
        ticket.get().setCustomer(customer.get());
    }

    public Category getCurrentCategory() {
        return currentCategory.get();
    }

    public void setCurrentCategory(Category category) throws ExceptionHandler {
        currentCategory.set(category);

        // Get all related words to that category and fulfill the observable list with that
        allAvailableWords.setAll(logic.getItemsByCategory(category));
    }

    public ObservableList<String> getMatchedItems() {
        return matchedItems;
    }

    public void checkSequence(String seq) {
        matchedItems.clear();
        List<String> matchedWords = logic.getMatchedWords(allAvailableWords, seq);
        if (matchedWords != null) {
            matchedItems.setAll(matchedWords);
        }
    }


    public ObservableList<String> getSelectedItems() {
        return selectedItems;
    }

    public void addItem(String item) {
        selectedItems.add(item);
    }

    public void removeItem(String item){
        selectedItems.remove(item);

    }

    public void setEvent(Event event){
        // Updates event object for the ticket
        this.event.set(event);

        // Adds the updated event object to the ticket
        ticket.get().setEvent(this.event.get());
    }

    public Event getEvent() {
        return event.get();
    }

    public void createTicket() throws ExceptionHandler{
        logic.createTicket(ticket.get());
    }

    public void clearAll(){
        allAvailableWords.clear();
        matchedItems.clear();
        selectedItems.clear();
        currentCategory.set(null);
    }

    public void setCustomerFirstName(String firstName) throws ExceptionHandler {
        this.customer.get().setFirstName(firstName);
    }

    public void setCustomerLastName(String lastName) throws ExceptionHandler {
        this.customer.get().setLastName(lastName);
    }

    public void setCustomerEmail(String email) throws ExceptionHandler {
        this.customer.get().setEmail(email);
    }
    public void setCustomerPhoneNumber(String phoneNumber) throws ExceptionHandler {
        this.customer.get().setPhoneNumber(phoneNumber);
    }

    public void setItemsTitle(List<String> itemsTitle) throws ExceptionHandler {
        List<Item> items = new ArrayList<>();
        for (String itemTitle: itemsTitle){
            Item item = new Item();
            item.setTitle(itemTitle);
            items.add(item);
        }
        this.ticket.get().setItems(items);
    }

    public Ticket getTicket() {
        return ticket.get();
    }
}
