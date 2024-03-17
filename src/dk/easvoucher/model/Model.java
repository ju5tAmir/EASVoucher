package dk.easvoucher.model;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.response.Response;
import dk.easvoucher.be.ticket.ITicket;
import dk.easvoucher.be.user.IUser;
import dk.easvoucher.be.user.User;
import dk.easvoucher.bll.LoginLogic;
import dk.easvoucher.bll.UserManager;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class Model {
    // Instantiate the logic component of the login page.
    private LoginLogic logic = new LoginLogic();
    private BooleanProperty loginStatus;
    private ObservableList<Event> eventsList = FXCollections.observableArrayList();
    private ObservableList<ITicket> ticketsList = FXCollections.observableArrayList();

    private IUser user;

    private UserManager userManager;

    /**
     * Constructor class
     */
    public Model(){
        // Set login getStatus to false when the class has just instantiated
        this.loginStatus = new SimpleBooleanProperty(false);
        this.userManager= new UserManager();
    }

    /**
     * Attempts to authenticate a user with the provided credentional
     * @param username the username of the user in plaintext
     * @param password the password of the user in plaintext
     */
    public void login(String username, String password) throws ExceptionHandler {
        // Sending login credentional to program logic for authentication
        Response response = logic.login(username, password);

        // Check if we got any response from logic, if not, it means the credentional were incorrect
        if (response.getStatus()) {
            // set received data from response
            setData(response);
        }
    }


    /**
     * Assign username, events and tickets values
     *
     * @param response response
     */
    private void setData(Response response){
        // Set login getStatus to true
        setLoginStatus(response.getStatus());
        // Set user from response
        setUser(response.getUser());
        // get events list
        setEvents(response.getEvents());
        // get tickets for the user
        setTickets(response.getTickets());
    }

    /**
     * set login getStatus to either true or false
     * @param status getStatus of the login
     */
    private void setLoginStatus(boolean status){
        loginStatus.set(status);
    }

    /**
     * get login getStatus
     */
    public BooleanProperty getLoginStatus() {
        return loginStatus;
    }

    /**
     * get user property
     * @return user
     */
    public IUser getUser(){
        return user;
    }

    private void setUser(IUser user){
        this.user = user;
    }

    private void setEvents(List<Event> events) {
        this.eventsList.addAll(events);
    }

    private void setTickets(List<ITicket> tickets){
        this.ticketsList.addAll(tickets);
    }

    public void createUser(User user) throws ExceptionHandler, SQLException {
        userManager.createUser(user);
    }

    public void removeUser(int id) throws ExceptionHandler, SQLException {
        userManager.removeUser(id);
    }
    public void updateUser(User user) throws ExceptionHandler, SQLException {
        userManager.updateUser(user);
    }

    public List<User> getAllUsers() throws SQLException, ExceptionHandler {
        return userManager.getAllUsers();
    }
}
