package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ku.cs.App;
import ku.cs.models.User;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameTF, passwordTF;

    @FXML
    private Text loginText;

    @FXML
    private Button signUpBtn, logInBtn;

    public void handleLogin(ActionEvent event){
        User napat = new User("napatswift", "napat", "napat_123");
        String username = usernameTF.getText();
        String pass = passwordTF.getText();

        System.out.println(napat.login(pass));

        if (napat.login(pass) == 2){
            loginText.setText("Login success");
        } else{
            loginText.setText("Username or password not correct");
        }
    }

    public void handleSignUp(ActionEvent event) throws IOException {
        App.setRoot("sign_up");
    }
}
