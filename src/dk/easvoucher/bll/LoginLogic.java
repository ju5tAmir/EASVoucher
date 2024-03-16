package dk.easvoucher.bll;

import dk.easvoucher.be.response.Response;
import dk.easvoucher.dal.LoginHandler;
import dk.easvoucher.exeptions.ExceptionHandler;

public class LoginLogic {

    private LoginHandler loginHandler;
    private Response response;

    public LoginLogic(){

        this.loginHandler = new LoginHandler();
        this.response = new Response();
    }

    /**
     * Authenticate with provided username and password
     * @param username username
     * @param password password
     * @return Response with StatusCode, User Object, EventsList, TicketsList
     */
    public Response login(String username, String password) throws ExceptionHandler {
        // Login with credentials
        response = loginHandler.handleLogin(username, password);

        // return response
        return response;
    }

}