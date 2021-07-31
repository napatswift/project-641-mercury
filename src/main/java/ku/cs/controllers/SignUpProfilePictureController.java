package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.App;
import ku.cs.models.Accounts;
import ku.cs.models.User;

import java.io.IOException;

public class SignUpProfilePictureController {
    private User currUser;
    private Accounts accounts;

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public void handleConfirmBtn(ActionEvent event) throws IOException{
        if (accounts.addAccount(currUser)){
            System.out.println(accounts.toCSV("data/users.csv"));
            System.out.println(accounts.toList());
            Button confirmBtn = (Button) event.getSource();

            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();

            if (this.accounts == null) {
                return;
            }

            loginController.setAccounts(this.accounts);

            Stage stage = (Stage) confirmBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 450, 650));
            stage.show();
        }
    }

    public void handleBack(ActionEvent event) throws IOException {
        Button backBtn = (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
        stage.setScene(new Scene(loader.load(), 450, 650));

        stage.show();
    }
}
