package dk.easvoucher.bll;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.dal.CoordinatorDAO;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorLogic {
    private CoordinatorDAO coordinatorDAO;


    public CoordinatorLogic(){
        this.coordinatorDAO = new CoordinatorDAO();
    }


    /**
     * Retrieves a list of events accessible to the specified coordinator.
     *
     * @param coordinator The coordinator for whom events are being fetched.
     * @return A list of Events object accessible to the coordinator.
     * @throws ExceptionHandler if there is any issue with fetching events from the database.
     */
    public List<Event> getAllEvents(Employee coordinator) throws ExceptionHandler {
        // Retrieve list of events which is accessible for the coordinator
        return coordinatorDAO.getEventsForCoordinator(coordinator);
    }

    public void setInfoBox(ListView<Label> eventInfoBox, Event event) {
        // List of labels that needs to be added into info box ListView
        List<Label> labels = new ArrayList<>();

        // Create label for ID
        Label id = new Label("ID: " + String.valueOf(event.getId()));
        labels.add(id);
        // Create label for name
        Label name = new Label("Name: " + event.getName());
        labels.add(name);
        // Create label for location
        Label location = new Label("Location: " + event.getLocation());
        labels.add(location);
        // Create host label (host coordinator)
        Label host = new Label("Hosted by: " + event.getCreatedBy().getUsername());
        labels.add(host);
        // Create label for start date
        Label startDate = new Label("Start Date: " + event.getStartDate().toString());
        labels.add(startDate);
        // Create label for start date
        Label startTime = new Label("Start Time: " + event.getStartTime().toString());
        labels.add(startTime);

        // Create label for start date (If any)
        if (event.getEndDate() != null) {
        Label endDate = new Label("End Date: " + event.getStartDate().toString());
        labels.add(endDate);
        }

        // Create label for start date (If any)
        if (event.getEndTime() != null) {
            Label endTime = new Label("End Time: " + event.getEndTime().toString());
            labels.add(endTime);
        }

        // Iterate over notes (If any)
        for (int n=1; n <= event.getNotes().size(); n++){
            Label noteLabel = new Label();
            noteLabel.setText("Note " + n + ": "+ event.getNotes().get(n-1).getNote());
            noteLabel.setPrefWidth(370);
            noteLabel.setWrapText(true);
            labels.add(noteLabel);
        }

        // Set labels list into ListView
        eventInfoBox.getItems().setAll(labels);
    }

    public List<ITicket> getAllTickets(Event event) throws ExceptionHandler {
        return coordinatorDAO.getAllTickets(event);
    }

    public void setTicketInfoBox(ListView<Label> ticketInfoBox, Ticket ticket) {
        // List of tickets that needs to be added into info box ListView
        List<Label> tickets = new ArrayList<>();


        // Create label for ID
        Label id = new Label("ID: " + ticket.getId());
        tickets.add(id);
        // Create customer label
        Label customer = new Label("Customer: " + ticket.getCustomer().getFirstName() + " " + ticket.getCustomer().getLastName());
        tickets.add(customer);
        // Create email label
        Label email = new Label("Email: " + ticket.getCustomer().getEmail());
        tickets.add(email);
        // Create email label
        Label phoneNumber = new Label("PhoneNo: " + ticket.getCustomer().getPhoneNumber());
        tickets.add(phoneNumber);
        // Create event label
        Label event = new Label("Event: " + ticket.getEvent().getName());
        tickets.add(event);
        // Create ticket type
        Label type = new Label("Type: " + ticket.getTicketType().toString());
        tickets.add(type);
        // Create ticket type

        // Iterate over items (If any)
        for (int n=1; n <= ticket.getItems().size(); n++){
            Label itemLabel = new Label();
            itemLabel.setText("Item " + n + ": "+ ticket.getItems().get(n-1).getTitle());
            itemLabel.setPrefWidth(370);
            itemLabel.setWrapText(true);
            tickets.add(itemLabel);
        }
        // Create UUID label
        Label uuid = new Label("UUID: " + ticket.getUUID().toString());
        tickets.add(uuid);

        // Set labels list into ListView
        ticketInfoBox.getItems().setAll(tickets);
    }

    public void deleteEvent(Event event) throws ExceptionHandler {
        coordinatorDAO.deleteEvent(event);
    }

    public void deleteTicket(Ticket ticket) throws ExceptionHandler {
        coordinatorDAO.deleteTicket(ticket);
    }
}
