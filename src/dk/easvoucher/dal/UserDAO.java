package dk.easvoucher.dal;

import dk.easvoucher.be.user.User;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.db.DBConnection;
import dk.easvoucher.exeptions.ExceptionHandler;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{
    private final DBConnection dbConnection;
    public UserDAO(){
        this.dbConnection= new DBConnection();
    }
    @Override
    public void createUser(User user) throws SQLException, ExceptionHandler {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (username, hashed_password, role) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Hash the password using bcrypt
            String hashedPassword = hashPassword(user.getPassword());

            statement.setString(1, user.getUsername());
            statement.setString(2, hashedPassword); // Save hashed password
            statement.setString(3, user.getRole().getValue());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    user.setId(id);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }
    private String hashPassword(String password) {
        // Generate a salt and hash the password using bcrypt
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    @Override
    public void removeUser(int id) throws SQLException, ExceptionHandler {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        }
    }
    @Override
    public List<User> getAllUsers() throws SQLException, ExceptionHandler {
        List<User> userList = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, username, role FROM users")) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String roleValue = resultSet.getString("role");

                    UserRole role = UserRole.fromString(roleValue);

                    User user = new User(id, username, role);
                    userList.add(user);
                }
            }
        }

        return userList;
    }


}
