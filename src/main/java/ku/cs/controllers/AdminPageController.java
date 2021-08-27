package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import ku.cs.models.AccountList;
import ku.cs.models.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AdminPageController {

    private Object[] data;
    private AccountList accountList;

    @FXML private Label nameAdmin
            ,role
            ,userName
            ,realNameUser
            ,lastLogin
            ,storeName;
    @FXML private ImageView imageView
            ,userImage;
    @FXML private ListView<User> userListView;;
    @FXML private TabPane adminTP;
    @FXML
    public void initialize() throws IOException {
        data = (Object[]) FXRouter.getData();
        User user = (User) data[0];
        accountList = (AccountList) data[1];

        showAdmin(user);
        showListView();
        clearSelectedUser();
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

    private void clearSelectedUser() {
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
        adminTP.getSelectionModel().select(1);
    }

    @FXML
    public void handleUserButton(ActionEvent actionEvent) {
        adminTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        adminTP.getSelectionModel().select(2);
    }

    public void handleResetPasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("reset_password", data);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า reset_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleAddCategoryButton(ActionEvent actionEvent) {
    }

    public void handleAddSubCategoryButton(ActionEvent actionEvent) {
    }
}
