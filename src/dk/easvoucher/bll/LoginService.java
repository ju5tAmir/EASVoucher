package dk.easvoucher.bll;

import dk.easvoucher.be.user.Employee;
import dk.easvoucher.dal.LoginDAO;
import dk.easvoucher.exeptions.ExceptionHandler;

public class LoginService {
    private final LoginDAO loginDAO;

    /**
     * Constructs a new LoginService instance.
     * Initializes the LoginHandler for managing user authentication.
     */
    public LoginService(){
        // Initializes the LoginHandler for managing user authentication.
        this.loginDAO = new LoginDAO();
    }

    /**
     * Authenticates the user by provided credentials.
     *
     * @param username The username of the user attempting to authenticate.
     * @param password The password of the user attempting to authenticate.
     * @return An employee object representing the authenticated user.
     * @throws ExceptionHandler If an error occurs during the authentication process.
     */
    public Employee getEmployee(String username, String password) throws ExceptionHandler {
        // Perform authentication using the provided credentials
        return loginDAO.loginAuth(username, password);
    }
}
