package ku.cs.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.Accounts;
import ku.cs.App;
import com.github.saacsos.FXRouter;
import ku.cs.models.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AdminControllerCategory{

    private User accounts;

    @FXML private Label nameAdmin
            ,role;
    @FXML private ImageView imageView;
    @FXML private Button handleLogOutButton
            ,handleCategoryButton
            ,handleUserButton
            ,handleReportButton
            ,handleMyAccountButton;

    @FXML
    public void initialize() throws FileNotFoundException {
        accounts = (User) FXRouter.getData();
        nameAdmin.setText(accounts.getName());
        role.setText(""+accounts.getRole());
        imageView.setImage(new Image(accounts.getPicturePath()));
    }

    @FXML
    private void handleLogOutButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleCategoryButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_category",accounts);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_category ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleUserButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_user",accounts);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_user ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleMyAccountButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_my_account",accounts);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_my_account ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_report",accounts);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
