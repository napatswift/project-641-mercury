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
import ku.cs.controllers.signup.SignUpController;
import ku.cs.models.Accounts;
import ku.cs.models.CsvReader;
import ku.cs.models.User;
import com.github.saacsos.FXRouter;

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

    public void removeErrorStyleClass(KeyEvent event){
        TextField textField = (TextField) event.getSource();
        textField.getStyleClass().removeAll("error-text-field");
    }

    public void addErrorStyleClass(TextField textField){
        textField.getStyleClass().add("error-text-field");
    }

    public void handleLogin(ActionEvent event) throws IOException {
        if (accounts == null) {
            populateUsers();
        }

        String username = usernameTF.getText();
        String password = passwordTF.getText();
        User currAcc = accounts.getUserAccount(username);

        if ( currAcc == null){
            addErrorStyleClass(passwordTF);
            addErrorStyleClass(usernameTF);
            loginText.setText("Username or password not correct");
            return;
        }

        int loginSuccess = currAcc.login(password);

        if (loginSuccess == 2){
            loginText.setText("Login success");
            accounts.toCsv("data/users.csv");
        } else if (loginSuccess == 0) {
            loginText.setText("Account has been banned");
            accounts.toCsv("data/users.csv");
        } else{
            addErrorStyleClass(passwordTF);
            addErrorStyleClass(usernameTF);
            loginText.setText("Password not correct");
        }
    }

    private void populateUsers() throws IOException {
        this.accounts = new Accounts();
        // username,role,name,password,picturePath,last_login,isBanned,loginAttempt,hasStore,store
        String [] lines = CsvReader.getLines("data/users.csv");

        for(String line: lines){
            String [] entries = line.split(",");
            User newUser = new User(entries[0], entries[1] , entries[2],
                    entries[3], entries[4], entries[5], entries[6], entries[7], entries[8], entries[9]);
            accounts.addAccount(newUser);
        }
    }

    public void handleSignUp(ActionEvent event) throws IOException {
        if(this.accounts == null) {
            populateUsers();
            if (this.accounts == null) {
                signUpBtn.setText("CANNOT SIGN-UP");
                return;
            }
        }

        FXRouter.goTo("sign_up", this.accounts);
    }

    public void handleAdmin(ActionEvent event){
        try {
            FXRouter.goTo("admin_page_my_account");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Admin Page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleHowTo(ActionEvent event){
        try {
            FXRouter.goTo("how_to_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า How To ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleAboutUs(ActionEvent event){
        try {
        FXRouter.goTo("about_us");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า About Us ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
