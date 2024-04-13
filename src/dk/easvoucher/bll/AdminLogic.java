package dk.easvoucher.bll;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.AdminDAO;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.util.List;

public class AdminLogic {

    AdminDAO dao ;

    public AdminLogic(){
        dao = new AdminDAO();
    }

    public List<Employee> getAllEmployees() throws ExceptionHandler {
        return dao.getAllEmployees();
    }

    public Employee createNewEmployee(String username, String password, UserRole role) throws ExceptionHandler{
        return dao.createEmployee(username, password, role);
    }

    public void removeUser(Employee employee) throws ExceptionHandler {
        dao.removeEmployee(employee);
    }

    public List<Event> getAllEvents() throws ExceptionHandler{
        return dao.getAllEvents();
    }

    public void removeEvent(Event event) throws ExceptionHandler {
        dao.removeEvent(event);
    }
}
