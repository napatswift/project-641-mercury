package ku.cs.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ku.cs.App;
import ku.cs.models.Accounts;
import ku.cs.models.User;

import java.io.IOException;

public class LoginController {
    public Accounts accounts;

    @FXML
    private TextField usernameTF, passwordTF;

    @FXML
    private Text loginText;

    @FXML
    private Button signUpBtn, logInBtn;

    @FXML
    public void handlePassword(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            logInBtn.fire();
        }
    }

    public void setAccounts(Accounts accounts){
        this.accounts = accounts;
    }

    public void handleLogin(ActionEvent event){
        if (accounts == null) {
            populateUsers();
        }

        String username = usernameTF.getText();
        String password = passwordTF.getText();
        User currAcc = accounts.getUserAccount(username);

        if ( currAcc == null){
            loginText.setText("Username not correct");
            return;
        }

        int loginSuccess = currAcc.login(password);

        if (loginSuccess == 2){
            loginText.setText("Login success");
        } else if (loginSuccess == 0) {
            loginText.setText("Account has been banned");
        } else{
            loginText.setText("Password not correct");
        }
    }

    private void populateUsers(){
        this.accounts = new Accounts();
        String [] lines = {
                "Napat,napatswift,napat_123",
                "Nong,nongswift,nong_123",
                "napatSwift,napatswift,napat_123",
                "napatS,napat,napat_123",
                "eie,neiio,40kvro"
        };
        for(String line: lines){
            String [] entries = line.split(",");
            User newUser = new User(entries[0], entries[1], entries[2]);
            accounts.addAccount(newUser);
        }
    }

    @FXML
    public void handleSignUp(ActionEvent event) throws IOException {
        Button signUpBtn = (Button) event.getSource();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("sign_up.fxml"));
        Parent root = loader.load();

        SignUpController signUpController = loader.getController();

        if(this.accounts == null) {
            populateUsers();
            if (this.accounts == null) {
                signUpBtn.setText("CANNOT SIGN-UP");
                return;
            }
        }

        signUpController.setAccounts(this.accounts);
        System.out.println(accounts.toList());
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        stage.setScene(new Scene(root, 450, 650));

        stage.show();
    }
}
