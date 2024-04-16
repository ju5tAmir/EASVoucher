package dk.easvoucher.bll;

import dk.easvoucher.be.ticket.Ticket;
import dk.easvoucher.dal.CreateTicketDAO;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.gui.dashboard.coordinator.ticket.create.Category;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateTicketLogic {
    private final CreateTicketDAO dao;


    public CreateTicketLogic() throws ExceptionHandler{
        this.dao = new CreateTicketDAO();
    }

    public List<String> getItemsByCategory(Category category) throws ExceptionHandler {
        if (category == Category.ITEM) {
            return dao.getExtraItems(category);
        } else {
            return dao.getItemsByCategory(category);
        }
    }


    public List<String> getMatchedWords(ObservableList<String> allAvailableWords, String sequence) {
        List<String> matchedWords = new ArrayList<>();
        if (sequence.isEmpty()){
            return null;
        }
        for (String word: allAvailableWords){
            if (word.toLowerCase().contains(sequence.toLowerCase())){
                matchedWords.add(word);
            }
        }
        return matchedWords;
    }


    public void createTicket(Ticket ticket) throws ExceptionHandler {
        // Checks if customer exists in the database for provided ticket
        // If exists, updates ticket's customer object properties like id
        // If not, creates new customer in database based on provided customer information
        if (!dao.isCustomerExists(ticket.getCustomer())){
            // Customer does not exist
            // Creates new customer in the database
            dao.createCustomer(ticket.getCustomer());
        }

        // Set UUID for the ticket
        ticket.setUUID(UUID.randomUUID());

        // Create ticket and link it to the related event and customer
        dao.createTicket(ticket);

        // If no exception happens, means ticket created successfully
    }
}
