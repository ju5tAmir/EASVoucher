package dk.easvoucher.dal;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler {

    private DatabaseConnection databaseConnection;

    public LoginHandler() {
        databaseConnection = new DatabaseConnection();
    }

    private void handleLogin(String username, String originalPassword) {
        String query = "select hashed_password, role from users where username = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("hashed_password");
                    if (BCrypt.checkpw(originalPassword, hashedPassword)){
                        //Password match
                        String userRole = resultSet.getString("role");

                        navigateBasedOnRole(userRole);
                    } else {
                        System.out.println("password do not match");
                    }
                } else {
                    System.out.println("username is not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void navigateBasedOnRole(String userRole) {
        System.out.println("User role: " + userRole);
    }
}
