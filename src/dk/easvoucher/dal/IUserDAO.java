package dk.easvoucher.dal;

import dk.easvoucher.be.user.User;
import dk.easvoucher.exeptions.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    void createUser(User user) throws SQLException, ExceptionHandler;

    void removeUser(int id) throws SQLException, ExceptionHandler;

    void updateUser(User user) throws SQLException, ExceptionHandler;


    List<User> getAllUsers() throws SQLException, ExceptionHandler;

}
