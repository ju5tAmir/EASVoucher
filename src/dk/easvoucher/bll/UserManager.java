package dk.easvoucher.bll;

import dk.easvoucher.be.user.User;
import dk.easvoucher.dal.IUserDAO;
import dk.easvoucher.dal.UserDAO;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;

public class UserManager {
    IUserDAO userDAO = new UserDAO();

    public UserManager(){

    }

    public void createUser(User user, String password) throws ExceptionHandler, SQLException {
        userDAO.createUser(user, password);
    }
    public void updateUser(User user) throws ExceptionHandler, SQLException {
        userDAO.updateUser(user);
    }
    public void removeUser(int id) throws ExceptionHandler, SQLException {
        userDAO.removeUser(id);
    }

    public List<User> getAllUsers() throws SQLException, ExceptionHandler {
        return userDAO.getAllUsers();
     }
}
