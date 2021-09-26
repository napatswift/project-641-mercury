package ku.cs.models;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Comparable<User>{

    public enum Role {ADMIN, USER}
    public Role role;
    private final String username;
    private String password;
    private final String name;
    private String picturePath;
    private LocalDateTime loginDateTime;
    protected boolean isBanned;
    private int loginAttempt;
    private boolean hasStore;
    private Store store;

    @Override
    public int compareTo(User other) {
        if (other.username.equals(this.username)) {
            return 0;
        }
        int compare = this.loginDateTime.compareTo(other.getLoginDateTime());
        if (compare == 0){
            return this.username.compareTo(other.username);
        } else {
            return compare;
        }
    }

    public User(String username, String name){
        this.username = username;
        this.name = name;
        this.isBanned = false;
        this.role = Role.USER;
        this.hasStore = false;
        this.loginDateTime = LocalDateTime.now();
    }

    public User(String username, String name, String password){
        this(username, name);
        this.setPassword(password);
    }

    public User(String username, Role role, String name, String password, String picturePath, LocalDateTime loginDateTime, boolean isBanned, int loginAttempt, boolean hasStore, Store store) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.name = name;
        this.picturePath = picturePath;
        this.loginDateTime = loginDateTime;
        this.isBanned = isBanned;
        this.loginAttempt = loginAttempt;
        this.hasStore = hasStore;
        this.store = store;
    }

    //getter
    public String getName() {
        return name;
    }

    /**
     *
     * @param password string
     * @return 0 user is being banned,
     * 1 password not match,
     * 2 login success
     */
    public int login(String password, boolean saveTime){
        if (this.isBanned){
            this.loginAttempt++;
            return 0;
        } else{
            if (password.equals(this.password)){
                if (saveTime) this.loginDateTime = LocalDateTime.now();
                if (this.loginAttempt > 0)
                    this.loginAttempt = 0;
                return 2;
            } else{
                return 1;
            }
        }
    }

    public int login(String password){
        return login(password, true);
    }

    public static boolean isUsername(String username){
        Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9_]{3,}$");
        Matcher matcher = passwordPattern.matcher(username);
        return matcher.find();
    }

    public static boolean isPassword(String password){
        Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9@$!%*#?&:+~{}<>_-]{6,25}$");
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.find();
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getStoreName() {return this.store.getName();}

    public Store getStore() {
        return store;
    }

    public String getPicturePath() {
        String picturePath = this.picturePath;
        if (this.picturePath == null || this.picturePath.equals("null"))
            picturePath = "media-cup-holder.png";
        return (new File(System.getProperty("user.dir") + File.separator + "/images" + File.separator + picturePath)).toURI().toString();
    }

    public LocalDateTime getLoginDateTime(){
        return loginDateTime;
    }

    public Boolean getHasStore() {return hasStore;}

    public boolean isBanned() {
        return isBanned;
    }

    //setter
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public boolean setPassword(String password) {
        if(isPassword(password)){
            this.password = password;
            return true;
        }else{
            return false;
        }
    }

    public void createStore(String storeName){
        if(this.store == null){
            this.store = new Store(storeName, username);
            hasStore = true;
        }
    }


    public String toCsv(){
        //username,role,name,password,picturePath,last_login,isBanned,loginAttempt,hasStore,store
        return username + ","
                + role + ","
                + "\"" + name + "\"" + ","
                + password + ","
                + picturePath + ","
                + (loginDateTime == null ? null : loginDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) + ","
                + isBanned + ","
                + loginAttempt + ","
                + hasStore + ","
                + (store == null ? null : store.getName());
    }
}

