package dk.easvoucher.bll;

import dk.easvoucher.be.user.User;
import dk.easvoucher.dal.AdminDAO;
import dk.easvoucher.dal.IUserDAO;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminManager {
    IUserDAO adminDAO = new AdminDAO();

    public AdminManager(){

    }

    public void createUser(User user, String password) throws ExceptionHandler, SQLException {
        adminDAO.createUser(user, password);
    }
    public void updateUser(User user) throws ExceptionHandler, SQLException {
        adminDAO.updateUser(user);
    }
    public void removeUser(int id) throws ExceptionHandler, SQLException {
        adminDAO.removeUser(id);
    }

    public List<User> getAllUsers() throws SQLException, ExceptionHandler {
        return adminDAO.getAllUsers();
     }
}
