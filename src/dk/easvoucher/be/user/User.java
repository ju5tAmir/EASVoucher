package dk.easvoucher.be;

public class User implements IUser {
    private int id;
    private String username;
    private UserRole role;

    @Override
    public int id() {
        return id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public UserRole role() {
        return role;
    }
}
