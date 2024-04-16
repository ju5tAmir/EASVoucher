package dk.easvoucher.gui.dashboard.admin;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.bll.AdminLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * author: <a href="https://github.com/NilIQW">Nil</a>
 */
public class AdminModel {
    private final SimpleObjectProperty<Employee> admin = new SimpleObjectProperty<>();
    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    private final ObservableList<Event> events = FXCollections.observableArrayList();

    AdminLogic logic = new AdminLogic();

    public Employee getAdmin() {
        return admin.get();
    }

    public void setAdmin(Employee admin){
        this.admin.set(admin);
    }

    public ObservableList<Employee> getEmployees() throws ExceptionHandler{
        setEmployees();
        return employees;
    }

    private void setEmployees() throws ExceptionHandler {
        this.employees.setAll(logic.getAllEmployees());
    }

    public void addUser(String username, String password, UserRole role) throws ExceptionHandler{
        Employee employee;
        employee = logic.createNewEmployee(username, password, role);
        employees.add(employee);
    }

    public void removeUser(Employee employee) throws ExceptionHandler{
        logic.removeUser(employee);
        employees.remove(employee);
    }

    public void updateUser(Employee employee, String newPassword) throws ExceptionHandler {
        logic.updateUser(employee, newPassword);
    }

    public ObservableList<Event> getAllEvents() throws ExceptionHandler{
        this.events.setAll(logic.getAllEvents());
        return events;
    }

    public void deleteEvent(Event event) throws  ExceptionHandler{
        logic.removeEvent(event);
        events.remove(event);
    }
}
