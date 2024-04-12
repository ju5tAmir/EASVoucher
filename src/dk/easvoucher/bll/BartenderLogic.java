package dk.easvoucher.bll;

import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.dal.BartenderDAO;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class BartenderLogic {

    BartenderDAO dao = new BartenderDAO();

    public BartenderLogic(){

    }

    public Ticket getTicket(String uuid) throws ExceptionHandler {
        return dao.getTicketByUUID(uuid);
    }

    public void setTicketInfoBox(ListView<Label> ticketInfoBox, Ticket ticket) {
        // List of tickets that needs to be added into info box ListView
        List<Label> tickets = new ArrayList<>();


        // Create label for ID
        Label id = new Label("ID: " + ticket.getId());
        tickets.add(id);
        // Create customer label
        System.out.println(ticket.getCustomer().toString());
        Label customer = new Label("Customer: " + ticket.getCustomer().getFirstName() + " " + ticket.getCustomer().getLastName());
        tickets.add(customer);
        // Create email label
        Label email = new Label("Email: " + ticket.getCustomer().getEmail());
        tickets.add(email);
        // Create email label
        Label phoneNumber = new Label("PhoneNo: " + ticket.getCustomer().getPhoneNumber());
        tickets.add(phoneNumber);
        // Create event label
//        Label event = new Label("Event: " + ticket.getEvent().getName());
//        tickets.add(event);
        // Create ticket type
        Label type = new Label("Type: " + ticket.getTicketType().toString());
        tickets.add(type);
        // Create ticket type


        // Create UUID label
        Label uuid = new Label("UUID: " + ticket.getUUID().toString());
        tickets.add(uuid);

        // Set labels list into ListView
        ticketInfoBox.getItems().setAll(tickets);
    }

    public void setExtraItems(ListView<CheckBox> ticketExtraItemsListView, Ticket ticket) {
        List<CheckBox> items = new ArrayList<>();
        // Iterate over items (If any)
        for (int n=1; n <= ticket.getItems().size(); n++){
            CheckBox checkBox = new CheckBox();
            checkBox.setText(ticket.getItems().get(n-1).getTitle());
            items.add(checkBox);
   }
        ticketExtraItemsListView.getItems().addAll(items);
    }
}
