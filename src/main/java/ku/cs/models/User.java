package ku.cs.models;

import ku.cs.models.io.CSVFile;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User implements Comparable<User>, CSVFile {

    public enum Role {ADMIN, USER}
    public Role role;
    private final String username;
    private String password;
    private final String name;
    private String pictureName;
    private LocalDateTime loginDateTime;
    protected boolean isBanned;
    private int loginAttempt;
    private boolean hasStore;
    private Store store;

    @Override
    public int compareTo(User other) {
        return this.username.compareTo(other.username);
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

    public User(String username, Role role, String name, String password, String pictureName, LocalDateTime loginDateTime, boolean isBanned, int loginAttempt) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.name = name;
        this.pictureName = pictureName;
        this.loginDateTime = loginDateTime;
        this.isBanned = isBanned;
        this.loginAttempt = loginAttempt;
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
        return username.matches("^[A-Za-z0-9_]{3,}$");
    }

    public static boolean isPassword(String password){
        return password.matches("^[A-Za-z0-9@$!%*#?&:+~{}<>_-]{6,25}$");
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
        String picturePath = this.pictureName;
        if (this.pictureName == null || this.pictureName.equals("null")) {
            URL path = getClass().getResource("/ku/cs/image/media-cup-holder.png");
            if (path != null)
                return path.toExternalForm();
        }
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
        this.pictureName = picturePath;
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
            this.store = new Store(this, storeName);
            hasStore = true;
        }
    }


    @Override
    public String toCSV(){
        return username + ","
                + role + ","
                + "\"" + name + "\"" + ","
                + password + ","
                + pictureName + ","
                + (loginDateTime == null ? null : loginDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) + ","
                + isBanned + ","
                + loginAttempt + ","
                + hasStore + ","
                + (store == null ? null : "\"" + store.getName().replace("\"", "\"\"") + "\"");
    }
}
