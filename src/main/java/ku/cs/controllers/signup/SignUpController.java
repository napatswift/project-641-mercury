package ku.cs.controllers.signup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import ku.cs.models.User;
import ku.cs.models.UserList;
import com.github.saacsos.FXRouter;
import ku.cs.service.DataSource;

import java.io.IOException;

public class SignUpController {
    private UserList userList;
    private DataSource dataSource;
    private String username;
    private String name;
    private String password;

    @FXML
    private TextField nameTF, usernameTF;

    @FXML
    private PasswordField passwordPF, confirmPasswordPF;

    @FXML
    private Text nameAssistiveText, usernameAssistiveText, passwordAssistiveText, confirmPasswordAssistiveText;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        this.userList = dataSource.getUserList();
    }


    public void addErrorStyleClass(TextField textField){
        textField.getStyleClass().removeAll("outline-text-field");
        textField.getStyleClass().add("error-outline-text-field");
    }

    public void handleBack(ActionEvent event){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

    public void handleFillName(KeyEvent event){

        TextField textField = (TextField) event.getSource();
        textField.getStyleClass().removeAll("error-outline-text-field");
        textField.getStyleClass().add("outline-text-field");

        String name = nameTF.getText();
        if (name.equals("")){
            addErrorStyleClass(nameTF);
            nameAssistiveText.setText("This must be filled");
        } else {
            this.name = name;
            nameAssistiveText.setText("");
        }
    }

    public void handleFillUsername(KeyEvent event){
        String username = usernameTF.getText();

        TextField textField = (TextField) event.getSource();
        textField.getStyleClass().removeAll("error-outline-text-field");
        textField.getStyleClass().add("outline-text-field");

        if (User.isPassword(passwordPF.getText())){
            passwordAssistiveText.setStyle("-fx-fill: rgba(0, 0, 0, 0.6);");
        } else{
            passwordAssistiveText.setStyle("-fx-fill: error-color;");
        }

        if (userList.isExist(username)){
            addErrorStyleClass(usernameTF);
            usernameAssistiveText.setText("username already existed");
        } else if (!User.isUsername(username)){
            this.username = null;
            usernameAssistiveText.setStyle("-fx-fill: error-color;");
            usernameAssistiveText.setText("more than 3 characters (a-z A-Z 0-9 _)");
        } else{
            usernameAssistiveText.setStyle("-fx-fill: rgba(0, 0, 0, 0.6);");
            this.username = username;
            usernameAssistiveText.setText("more than 3 characters (a-z A-Z 0-9 _)");
        }
    }

    public void handleFillPassword(KeyEvent event){

        PasswordField passwordField = (PasswordField) event.getSource();
        passwordField.getStyleClass().removeAll("error-outline-text-field");
        passwordField.getStyleClass().add("outline-text-field");

        String password = passwordPF.getText();

        if (this.password != null){
            String confirmPassword = confirmPasswordPF.getText();
                if (!confirmPassword.equals(password)){
                    this.password = null;
                    confirmPasswordPF.setText("");
                    addErrorStyleClass(passwordPF);
                    confirmPasswordAssistiveText.setText("Password not match!");
                } else{
                    this.password = password;
                    confirmPasswordPF.getStyleClass().removeAll("error-outline-text-field");
                    confirmPasswordPF.getStyleClass().add("outline-text-field");
                    confirmPasswordAssistiveText.setText("Password matched!");
                }
        }

        if (this.username == null || this.name == null || this.username.equals("") || this.name.equals("")) {
            if (this.username == null || username.equals("")) {
                addErrorStyleClass(usernameTF);
                usernameAssistiveText.setText("this must be filled");
            }
            if (this.name == null || name.equals("")) {
                addErrorStyleClass(nameTF);
                nameAssistiveText.setText("this must be filled");
            }
            return;
        }

        if (User.isPassword(passwordPF.getText())){
            passwordAssistiveText.setStyle("-fx-fill: rgba(0, 0, 0, 0.6);");
        } else{
            passwordAssistiveText.setStyle("-fx-fill: error-color;");
        }

    }

    public void handleFillConfirmPassword(KeyEvent event) {

        PasswordField passwordField = (PasswordField) event.getSource();
        passwordField.getStyleClass().removeAll("error-outline-text-field");
        passwordField.getStyleClass().add("outline-text-field");

        String password = passwordPF.getText();
        if(User.isPassword(password)){
            String confirmPassword = confirmPasswordPF.getText();
            if (confirmPassword.length() >= password.length()){
                if (!confirmPassword.equals(password)){
                    addErrorStyleClass(passwordPF);
                    addErrorStyleClass(confirmPasswordPF);
                    confirmPasswordAssistiveText.setText("Password not match!");
                    confirmPasswordAssistiveText.setStyle("-fx-fill: error-color;");
                } else{
                    this.password = password;
                    passwordPF.getStyleClass().removeAll("error-outline-text-field");
                    passwordPF.getStyleClass().add("outline-text-field");
                    confirmPasswordAssistiveText.setText("Password matched!");
                    confirmPasswordAssistiveText.setStyle("");
                }
            }
        } else{
            passwordAssistiveText.setStyle("-fx-fill: error-color;");
        }
    }

    public void handleSubmit(ActionEvent event) throws IOException {
        if(this.password == null || this.username == null || this.name == null){
            if (this.password == null){
                addErrorStyleClass(passwordPF);
                addErrorStyleClass(confirmPasswordPF);
            }
            if (this.username == null){
                addErrorStyleClass(usernameTF);
            }
            if (this.name == null){
                addErrorStyleClass(nameTF);
            }
            return;
        }

        String password = passwordPF.getText();
        String confirmPassword = confirmPasswordPF.getText();

        if(!password.equals(confirmPassword)){
            addErrorStyleClass(passwordPF);
            addErrorStyleClass(confirmPasswordPF);
            confirmPasswordAssistiveText.setText("Password not match!");
            return;
        }

        User newUser = new User(this.username, this.name);
        if (newUser.setPassword(this.password)){
            if(this.userList == null) {
                return;
            }
            Object[] data = {newUser, dataSource};
            FXRouter.goTo("sign_up_profile_picture", data);
        } else{
            passwordAssistiveText.setStyle("-fx-fill: error-color;");
        }
    }
}
