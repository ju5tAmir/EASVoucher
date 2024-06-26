package dk.easvoucher.bll;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.dal.CreateEventDAO;

import dk.easvoucher.exeptions.ExceptionHandler;
import java.util.List;

public class CreateEventLogic {
    private final CreateEventDAO dao = new CreateEventDAO();

    public List<Employee> getCoordinatorsList() throws ExceptionHandler {
        return dao.getAllCoordinators();
    }

    public void createEvent(Event event) throws ExceptionHandler{
        dao.createEvent(event);
    }
}
