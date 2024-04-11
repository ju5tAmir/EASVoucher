package dk.easvoucher.be.user;

public class User implements IUser {
    private int id;
    private String username;
    private UserRole role;


    public User(int id, String username, UserRole role){
        this.id = id;
        this.username = username;
        this.role = role;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public UserRole getRole() {
        return role;
    }

    @Override
    public void setRole(UserRole role) {
        this.role = role;
    }
}
