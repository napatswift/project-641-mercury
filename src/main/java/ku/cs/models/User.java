package ku.cs.models;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Comparable<User>{
    public enum Role {ADMIN, USER}
    public Role role;
    private String username;
    private String password;
    private String name;
    private String picturePath;
    private LocalDateTime loginDateTime;
    private boolean isBanned;
    private int loginAttempt;
    private boolean hasStore;
    private Store store;

    @Override
    public int compareTo(User other) {
        if (other.getUsername().equals(this.getUsername())) {
            return 0;
        }
        return 1;
    }

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

    // username,role,name,password,picturePath,last_login,isBanned,loginAttempt,hasStore,store
    public User(String username, String role, String name, String password, String picturePath, String loginDateTime, String isBanned, String loginAttempt, String hasStore, String store) {
        this.role = role.equals("USER") ? Role.USER : Role.ADMIN;
        this.username = username;
        this.password = password;
        this.name = name;
        this.picturePath = picturePath.equals("null") ? null : picturePath;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.loginDateTime = loginDateTime.equals("null") ? null : LocalDateTime.parse(loginDateTime, formatter);

        this.isBanned = isBanned.equals("TRUE");
        this.loginAttempt = Integer.parseInt(loginAttempt);
        this.hasStore = hasStore.equals("TRUE");
        this.store = this.hasStore ? new Store(store, this) : null;
    }

    //getter
    public String getName() {
        return name;
    }

    public int login(String password){
        /**
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
                this.loginDateTime = LocalDateTime.now();
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
        String picturePath = this.picturePath;
        if (this.picturePath == null){
            picturePath = "media-cup-holder.png";
        }
        return (new File(System.getProperty("user.dir") + File.separator + "/images" + File.separator + picturePath)).toURI().toString();
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

    public static boolean isPassword(String password){
        Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9@$!%*#?&:+~{}<>_-]{6,25}$");
        Matcher matcher = passwordPattern.matcher(password);
        if (matcher.find()){
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

    public String toCSV(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //username,role,name,password,picturePath,last_login,isBanned,loginAttempt,hasStore,store
        return username + ","
                + role + ","
                + name + ","
                + password + ","
                + picturePath + ","
                + (loginDateTime == null ? null : loginDateTime.format(formatter)) + ","
                + isBanned + ","
                + loginAttempt + ","
                + hasStore + ","
                + (hasStore ? store.getName() : null);
    }
}