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
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        stage.setScene(new Scene(root, 450, 700));

        stage.show();
    }

    public void handleAdmin(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("admin_page_my_account");
    }
}
