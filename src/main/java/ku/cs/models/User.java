package ku.cs.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    public enum Role {ADMIN, USER}
    public Role role;
    private String username;
    private String password;
    private String name;
    private String picturePath;
    private boolean isBanned;
    private int loginAttempt;
    private boolean hasStore;
    private Store store;

    public User(String username, String name){
        this.username = username;
        this.name = name;
        this.isBanned = false;
        this.role = Role.USER;
        this.hasStore = false;
    }

    public User(String username, String name, String password){
        this(username, name);
        this.setPassword(password);
    }

    //getter
    public String getName() {
        return name;
    }

    public int login(String password){
        /*
         * return value description
         *
         * 0 user is being banned
         * 1 password not match
         * 2 login success
         */

        if (this.isBanned){
            this.loginAttempt++;
            return 0;
        } else{
            if (password.equals(this.password)){
                return 2;
            } else{
                return 1;
            }
        }
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPicturePath() {
        return picturePath;
    }

    //setter
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public boolean setPassword(String password) {

        Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9@$!%*#?&:+~{}<>_-]{6,25}$");
        Matcher matcher = passwordPattern.matcher(password);
        if (matcher.find()){
            this.password = password;
            return true;
        } else{
            return false;
        }
    }

    public boolean setIsBannedBy(User other){
        if (other.getRole() == Role.ADMIN & this.role == Role.USER){
            this.isBanned = true;
            return true;
        } else{
            return false;
        }
    }

    public void openStore(String name){
        this.store = new Store(name, this);
        this.hasStore = true;
    }
}