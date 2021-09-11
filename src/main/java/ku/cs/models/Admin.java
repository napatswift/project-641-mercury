package ku.cs.models;

import java.time.LocalDateTime;

public class Admin extends User {
    public Admin(String username, Role role, String name, String password, String picturePath, LocalDateTime loginDateTime, boolean isBanned, int loginAttempt, boolean hasStore, Store store) {
        super(username, role, name, password, picturePath, loginDateTime, isBanned, loginAttempt, hasStore, store);
    }

    public boolean bans(User other) {
        if (this.getRole() == Role.ADMIN & other.role == Role.USER) {
            other.isBanned = true;
            return true;
        } else
            return false;
    }

    public boolean unbans(User other) {
        if (this.getRole() == Role.ADMIN & other.role == Role.USER) {
            other.isBanned = false;
            return true;
        } else {
            return false;
        }
    }

}
