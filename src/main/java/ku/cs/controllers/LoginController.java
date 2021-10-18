package ku.cs.controllers;

import com.github.saacsos.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.models.components.theme.ThemeMenu;
import ku.cs.service.DataSource;

import java.io.IOException;

public class LoginController {
    private UserList userList;
    private DataSource dataSource;

    @FXML
    private TextField usernameTF, passwordTF;

    @FXML
    private Text loginText, appNameText;

    @FXML
    private Button signUpBtn, logInBtn;

    @FXML
    private VBox bottomButtonVB;

    public void handlePassword(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            logInBtn.fire();
        }
    }

    public void initialize(){
        dataSource = new DataSource("data");
        dataSource.parseAccount();
        userList = dataSource.getUserList();
        appNameText.setText("MERCURY");

        addThemeMenu();
        passwordTF.setOnKeyReleased(this::handlePassword);
    }

    public void addThemeMenu(){
        ThemeMenu themeMenu = new ThemeMenu();
        themeMenu.getStyleClass().add("primary-color-menu-button");
        bottomButtonVB.getChildren().add(themeMenu);
    }

    public void removeErrorStyleClass(KeyEvent event){
        TextField textField = (TextField) event.getSource();
        textField.getStyleClass().removeAll("error-text-field");
    }

    public void addErrorStyleClass(TextField textField){
        textField.getStyleClass().add("error-text-field");
    }

    public void handleLogin(){
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        User currAcc = userList.getUser(username);

        if ( currAcc == null){
            addErrorStyleClass(passwordTF);
            addErrorStyleClass(usernameTF);
            loginText.setText("Username or password not correct");
            return;
        }

        int loginSuccess = currAcc.login(password);

        if (loginSuccess == 2){
            loginText.setText("Login success");
            dataSource.saveAccount();
            userList.login(username, password);
            if(currAcc.getRole() == User.Role.ADMIN)
            {
                try {
                    FXRouter.goTo("admin_page", dataSource);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("ไปที่หน้า Admin Page ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            } else if (currAcc.getRole() == User.Role.USER){
                try {
                    FXRouter.goTo("marketplace", dataSource);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("ไปที่หน้า marketplace ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        } else if (loginSuccess == 0) {
            loginText.setText("User has been banned");
            dataSource.saveAccount();
        } else{
            addErrorStyleClass(passwordTF);
            addErrorStyleClass(usernameTF);
            loginText.setText("Username or password not correct");
        }
    }

    public void handleSignUp() {
        if(this.userList == null) {
            signUpBtn.setText("CANNOT SIGN-UP");
            return;
        }

        try {
            FXRouter.goTo("sign_up", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleHowTo(){
        try {
            FXRouter.goTo("how_to");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า How To ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleAboutUs(){
        try {
        FXRouter.goTo("about_us");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า About Us ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
