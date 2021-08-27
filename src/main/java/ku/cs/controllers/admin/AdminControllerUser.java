package ku.cs.controllers.admin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.AccountList;
import com.github.saacsos.FXRouter;
import ku.cs.models.CsvReader;
import ku.cs.models.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AdminControllerUser {

    private User user;
    private AccountList accountList;

    @FXML private Label nameAdmin
            ,role
            ,userName
            ,realNameUser
            ,lastLogin
            ,storeName;
    @FXML private ImageView imageView
            ,userImage;
    @FXML private Button handleLogOutButton
            ,handleCategoryButton
            ,handleUserButton
            ,handleReportButton
            ,handleMyAccountButton;

    @FXML private ListView<User> userListView;

    @FXML
    public void initialize() throws IOException {
        user = (User) FXRouter.getData();
        showAdmin(user);
        showListView();
        clearSelectedMemberCard();
        handleSelectedListView();
    }

    public void showAdmin(User user){
        nameAdmin.setText(user.getName());
        role.setText(""+ user.getRole());
        imageView.setImage(new Image(user.getPicturePath()));
    }


    private void showListView() throws IOException {
        if(accountList == null)
            populateUsers();
        for(User user : accountList.toList()) {
            if(user.getRole() == User.Role.USER){
                userListView.getItems().add(user);
            }
        }
        userListView.refresh();
    }

    private void handleSelectedListView() {
        userListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<User>() {
                    @Override
                    public void changed(ObservableValue<? extends User> observable,
                                        User oldValue, User newValue) {
                        System.out.println(newValue + " is selected");
                        showSelectedUser(newValue);
                    }
                });
    }

    private void showSelectedUser(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        userImage.setImage(new Image(user.getPicturePath()));
        userName.setText(user.getUsername());
        realNameUser.setText(user.getName());
        lastLogin.setText("last login "+user.getLoginDateTime().format(formatter));
        if(!user.getStoreName().equals("null")){
            storeName.setText(user.getStoreName());
        }
        else
            storeName.setText("This User Don't Have Store");
    }

    private void clearSelectedMemberCard() {
        userName.setText("");
        realNameUser.setText("");
        lastLogin.setText("");
        storeName.setText("");
    }

    private void populateUsers() throws IOException {
        this.accountList = new AccountList();
        // username,role,name,password,picturePath,last_login,isBanned,loginAttempt,hasStore,store
        String [] lines = CsvReader.getLines("data/users.csv");

        for(String line: lines){
            String [] entries = line.split(",");
            User newUser = new User(entries[0], entries[1] , entries[2],
                    entries[3], entries[4], entries[5], entries[6], entries[7], entries[8], entries[9]);
            accountList.addAccount(newUser);
        }
    }

    @FXML
    private void handleLogOutButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleCategoryButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_category", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_category ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleUserButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_user", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_user ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleMyAccountButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_my_account", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_my_account ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_report", user);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
