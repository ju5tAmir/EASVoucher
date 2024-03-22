package dk.easvoucher.be.entities;

public class User {
    private int userId;
    private String username;
    private String hashedPassword;
    private Integer roleId;

    public User(int userId, String username, String hashedPassword, Integer roleId) {
        this.userId = userId;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}