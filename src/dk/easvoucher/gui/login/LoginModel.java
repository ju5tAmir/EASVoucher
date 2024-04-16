package dk.easvoucher.gui.login;

import dk.easvoucher.be.user.Employee;
import dk.easvoucher.bll.LoginService;
import dk.easvoucher.exeptions.ExceptionHandler;

import javafx.beans.property.SimpleObjectProperty;
public class LoginModel {
    private final LoginService loginService = new LoginService();
    private final SimpleObjectProperty<Employee> loggedInEmployee = new SimpleObjectProperty<>();

    /**
     * Default constructor for LoginModel.
     *
     */
    public LoginModel(){
    }

    /**
     * Attempts to authenticate a user with the provided credentials
     *
     * @param username the username of the user in plaintext
     * @param password the password of the user in plaintext
     * @throws ExceptionHandler if an error occurs during the authentication process.
     */
    public void authenticate(String username, String password) throws ExceptionHandler{
        // creates employee object if credentials were correct
        Employee authenticatedEmployee = loginService.getEmployee(username, password);

        // Sets the employee object as logged-in employee
        setLoggedInEmployee(authenticatedEmployee);
    }

    /**
     * Retrieve the currently logged-in employee object.
     *
     * @return logged in employee object
     */
    public Employee getLoggedInEmployee(){
        return loggedInEmployee.getValue();
    }

    /**
     * Sets the logged-in employee.
     *
     * @param employee the employee object to set as logged-in.
     */
    private void setLoggedInEmployee(Employee employee){
        this.loggedInEmployee.set(employee);
    }
}
