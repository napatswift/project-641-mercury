package ku.cs.controllers;

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
import ku.cs.models.CSVReader;
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

    public void handleLogin(ActionEvent event) throws IOException {
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
            System.out.println(accounts.toCSV("data/users.csv"));
        } else if (loginSuccess == 0) {
            loginText.setText("Account has been banned");
        } else{
            loginText.setText("Password not correct");
        }
        System.out.println(accounts.toCSV("data/users.csv"));
    }

    private void populateUsers() throws IOException {
        this.accounts = new Accounts();
        // username,role,name,password,picturePath,last_login,isBanned,loginAttempt,hasStore,store
        String [] lines = CSVReader.getLines("data/users.csv");

        for(String line: lines){
            System.out.println(line);
        }

        for(String line: lines){
            String [] entries = line.split(",");
            System.out.println(line);
            User newUser = new User(entries[0], entries[1] , entries[2],
                    entries[3], entries[4], entries[5], entries[6], entries[7], entries[8], entries[9]);

            accounts.addAccount(newUser);
        }
    }

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
