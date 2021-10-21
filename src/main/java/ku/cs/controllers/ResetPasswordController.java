package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.User;
import ku.cs.models.UserList;
import com.github.saacsos.FXRouter;
import ku.cs.service.DataSource;

import java.io.IOException;

public class ResetPasswordController {

    private User user;
    private DataSource dataSource;


    @FXML
    private TextField confirmNewPasswordTextField;

    @FXML
    private Label description;

    @FXML
    private Button holdBackButton;

    @FXML
    private Button holdResetPasswordButton;

    @FXML
    private Label name;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField oldPasswordTextField;

    @FXML
    private ImageView userImageView;

    @FXML
    private Label userName;

    @FXML
    public void initialize() throws IOException {
        dataSource = (DataSource) FXRouter.getData();
        user = dataSource.getUserList().getCurrUser();
        showUser(user);
    }

    public void setHandleBackButton(EventHandler<ActionEvent> handler) {
        holdBackButton.setOnAction(handler);
    }

    public boolean resetPassword(){
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmNewPassword = confirmNewPasswordTextField.getText();
        if (user.login(oldPassword, false) == 2) {
            if(newPassword.equals(confirmNewPassword) && User.isPassword(newPassword)){
                user.setPassword(newPassword);
                dataSource.saveAccount();
                return true;
            } else if (!User.isPassword(newPassword)){
                description.setText("Your new password invalid password format");
            } else {
                description.setText("Your new password not match.");
            }
        } else{
            description.setText("Your old password is wrong.");
        }
        return false;
    }

    private void showUser(User user){
        userName.setText(user.getUsername());
        name.setText(user.getName());
        userImageView.setImage(new Image(user.getPicturePath()));
    }

    public void holdResetPasswordButton(ActionEvent actionEvent) {
        boolean check = resetPassword();
        if (check) {
            try {
                com.github.saacsos.FXRouter.goTo("login");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า login ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
