package ku.cs.models;

public class Admin extends User{

    public Admin(String username, String name){
        super(username, name);
        this.role = Role.ADMIN;
    }

    public Admin(String username, String password, String name) {
            this(username, name);
            this.setPassword(password);
    }

    public void ban(User other){
        if (other.getRole() == Role.USER) {
            other.setIsBannedBy(this);
        } else{
            ; /* this user is admin, cannot ban this user*/
        }
    }
}
