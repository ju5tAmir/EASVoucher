package dk.easvoucher.be.user;

public class User implements IUser {
    private int id;
    private String username;
    private UserRole role;

    private String password;

    public User() {
    }

    public User(int id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password=password;
        this.role = role;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public UserRole getRole() {
        return role;
    }
}