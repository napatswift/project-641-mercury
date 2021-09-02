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

    public User( String username, Role role, String name, String password, String picturePath, LocalDateTime loginDateTime, boolean isBanned, int loginAttempt, boolean hasStore, Store store) {
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
                if (this.loginAttempt > 0)
                    this.loginAttempt = 0;
                return 2;
            } else{
                return 1;
            }
        }
    }

    public static boolean isUsername(String username){
        Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9_]{3,}$");
        Matcher matcher = passwordPattern.matcher(username);
        if (matcher.find()){
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

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getStoreName() {return this.store.getName();}

    public String getPicturePath() {
        String picturePath = this.picturePath;
        if (this.picturePath.equals("null")){
            picturePath = "media-cup-holder.png";
        }
        return (new File(System.getProperty("user.dir") + File.separator + "/images" + File.separator + picturePath)).toURI().toString();
    }

    public LocalDateTime getLoginDateTime(){
        return loginDateTime;
    }

    public Boolean getHasStore() {return hasStore;}

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

    public boolean setIsBannedBy(User other){
        if (other.getRole() == Role.ADMIN & this.role == Role.USER){
            this.isBanned = true;
            return true;
        } else{
            return false;
        }
    }

    public void createStore(String storeName){
        if(this.store == null){
            this.store = new Store(storeName, username);
            hasStore = true;
        }
    }
    public void openStore(String name){
        this.hasStore = true;
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return  loginDateTime.format(formatter) + "\n"
                + username;
    }

    public static class UserListCell extends ListCell<User> {
        private final VBox content;
        private final Label topLabel;
        private final Label label;

        public UserListCell() {
            topLabel = new Label();
            label = new Label();
            topLabel.getStyleClass().add("body2");
            topLabel.setStyle("-fx-text-fill: on-surface-med-color;");
            label.getStyleClass().add("body1");
            label.setStyle("-fx-text-fill: on-surface-color");
            content = new VBox(topLabel, label);
            content.setSpacing(3);
        }

        private String getTimeString(LocalDateTime time){
            String pattern = "HH:mm - d MMM";
            DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
            return simpleDateFormat.format(time);
        }

        @Override
        protected void updateItem(User item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) {
                topLabel.setText(getTimeString(item.getLoginDateTime()));
                label.setText(item.getUsername());
                setGraphic(content);
                setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: surface-overlay");
            } else {
                setGraphic(null);
            }
        }
    }
}

