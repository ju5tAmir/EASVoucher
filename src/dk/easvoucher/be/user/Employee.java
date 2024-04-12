package dk.easvoucher.be.user;

public class Employee implements IUser {
    private int id;
    private String username;
    private UserRole role;

    public Employee(){

    }

    public Employee(int id, String username, UserRole role){
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
