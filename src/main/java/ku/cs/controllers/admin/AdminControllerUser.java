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
import ku.cs.models.User;
import ku.cs.service.UserDataSource;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class AdminControllerUser {

    private AccountList accountList;
    private Object[] data;

    @FXML private Label nameAdmin
            ,role
            ,userName
            ,realNameUser
            ,lastLogin
            ,storeName;
    @FXML private ImageView imageView
            ,userImage;
    @FXML private ListView<User> userListView;

    @FXML
    public void initialize() throws IOException {
        data = (Object[]) FXRouter.getData();
        User user = (User) data[0];
        accountList = (AccountList) data[1];

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
        for(User user : accountList.toListReverse()) {
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
            FXRouter.goTo("admin_page_category", data);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_category ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleUserButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_user", data);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_user ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_report", data);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleResetPasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("reset_password", data);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า reset_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
