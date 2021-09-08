package ku.cs.models;

import java.time.LocalDateTime;

public class Admin extends User {
    public Admin(String username, Role role, String name, String password, String picturePath, LocalDateTime loginDateTime, boolean isBanned, int loginAttempt, boolean hasStore, Store store) {
        super(username, role, name, password, picturePath, loginDateTime, isBanned, loginAttempt, hasStore, store);
    }

    public boolean setIsBanned(User other) {
        if (this.getRole() == Role.ADMIN & other.role == Role.USER) {
            other.setBanned(true);
            return true;
        } else
            return false;
    }

    public boolean setIsUnbanned(User other) {
        if (this.getRole() == Role.ADMIN & other.role == Role.USER) {
            other.setBanned(false);
            return true;
        } else {
            return false;
        }
    }

}
