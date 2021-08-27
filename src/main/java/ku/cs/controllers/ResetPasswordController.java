package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.AccountList;
import ku.cs.models.User;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class ResetPasswordController {

    private User user;
    private AccountList accountList;
    private Object[] data;

    @FXML private TextField oldPasswordTextField
            ,newPasswordTextField
            ,confirmNewPasswordTextField;

    @FXML private Label userName
            ,name
            ,description;

    @FXML private ImageView userImageView;

    @FXML
    public void initialize() throws IOException {
        data = (Object[]) FXRouter.getData();
        user = (User) data[0];
        accountList = (AccountList) data[1];
        showUser(user);
    }

    public int resetPassword(){
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmNewPassword = confirmNewPasswordTextField.getText();
        if(user.login(oldPassword) == 2){
            if(newPassword.equals(confirmNewPassword) && User.isPassword(newPassword)){
                user.setPassword(newPassword);
                accountList.toCsv("data/users.csv");
                return 1;
            }
            else if(!User.isPassword(newPassword)){
                description.setText("Your new password invalid password format");
                return 0;
            }
            else {
                description.setText("Your new password not match.");
                return 0;
            }
        }
        else{
            description.setText("Your old password is wrong.");
            return 0;
        }
    }


    public void showUser(User user){
        userName.setText(user.getUsername());
        name.setText(user.getName());
        userImageView.setImage(new Image(user.getPicturePath()));
    }

    public void holdBackButton(ActionEvent actionEvent) {
        if(user.getRole() == User.Role.ADMIN) {
            try {
                com.github.saacsos.FXRouter.goTo("admin_page_user", data);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า admin_page_user ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        else{
            try {
                com.github.saacsos.FXRouter.goTo("marketplace", data);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    public void holdResetPasswordButton(ActionEvent actionEvent) {
        int check = resetPassword();
        if(check == 1){
            try {
                com.github.saacsos.FXRouter.goTo("login");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า login ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        else
            return;
    }
}
