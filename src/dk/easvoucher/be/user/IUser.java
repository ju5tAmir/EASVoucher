package dk.easvoucher.be.user;

import dk.easvoucher.be.user.UserRole;

public interface IUser {
    int id();
    String username();
    UserRole role();

}
