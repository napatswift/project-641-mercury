package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ku.cs.App;
import ku.cs.models.Accounts;
import ku.cs.models.User;

import java.io.IOException;

public class SignUpController {
    private Accounts accounts;
    private String username;
    private String name;
    private String password;

    @FXML
    private TextField nameTF, usernameTF;

    @FXML
    private PasswordField passwordPF, confirmPasswordPF;

    @FXML
    private Text nameAssistiveText, usernameAssistiveText, passwordAssistiveText, confirmPasswordAssistiveText;

    public void handleBack(ActionEvent event) throws IOException {
        Button backBtn = (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        stage.setScene(new Scene(loader.load(), 450, 650));

        stage.show();
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public void handleFillName(KeyEvent event){
        String name = nameTF.getText();
        if (name.equals("")){
            nameAssistiveText.setText("This must be filled");
        } else {
            this.name = name;
            nameAssistiveText.setText("");
        }
    }

    public void handleFillUsername(KeyEvent event){
        String username = usernameTF.getText();
        if(username.length() < 5){
            usernameAssistiveText.setText("");
            return;
        }
        if (accounts.isExist(username)){
            usernameAssistiveText.setText("username already existed");
        } else{
            usernameAssistiveText.setText("username is preserved for you!");
            this.username = username;
        }
    }

    public void handleFillPassword(KeyEvent event){
        if (this.username == null || this.name == null || this.username.equals("") || this.name.equals("")) {
            if (this.username == null || username.equals("")) {
                usernameAssistiveText.setText("this must be filled");
            }
            if (this.name == null || name.equals("")) {
                nameAssistiveText.setText("this must be filled");
            }
            return;
        }
        if (User.isPassword(passwordPF.getText())){
            passwordAssistiveText.setStyle("-fx-fill: rgba(0, 0, 0, 0.6);");
        }
    }

    public void handleFillConfirmPassword(KeyEvent event) {
        String password = passwordPF.getText();
        if(User.isPassword(password)){
            String confirmPassword = confirmPasswordPF.getText();
            if (confirmPassword.length() >= password.length()){
                if (!confirmPassword.equals(password)){
                    confirmPasswordAssistiveText.setText("Password not match!");
                } else{
                    this.password = password;
                    confirmPasswordAssistiveText.setText("Password matched!");
                }
            }
        } else{
            passwordAssistiveText.setStyle("-fx-fill: error-color;");
        }
    }

    public void handleSubmit(ActionEvent event) throws IOException {
        if(this.password == null || this.username == null || this.name == null){
            return;
        }
        User newUser = new User(this.username, this.name);
        if (newUser.setPassword(this.password)){
            Button signUpBtn = (Button) event.getSource();

            FXMLLoader loader = new FXMLLoader(App.class.getResource("sign_up_profile_picture.fxml"));
            Parent root = loader.load();

            SignUpProfilePictureController signUpProfilePictureController = loader.getController();

            if(this.accounts == null) {
                return;
            }

            signUpProfilePictureController.setAccounts(this.accounts);
            signUpProfilePictureController.setCurrUser(newUser);

            Stage stage = (Stage) signUpBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 450, 650));

            stage.show();

        } else{
            passwordAssistiveText.setStyle("-fx-fill: error-color;");
        }
    }
}
