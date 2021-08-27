package ku.cs.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import ku.cs.models.User;
import ku.cs.service.DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AdminControllerReport {

    private DataSource dataSource;

    @FXML private Label nameAdmin
            ,role;
    @FXML private ImageView imageView;

    @FXML
    public void initialize() throws FileNotFoundException {
        dataSource = (DataSource) FXRouter.getData();
        User user = dataSource.getAccounts().getCurrAccount();
        showAdmin(user);
    }

    public void showAdmin(User user){
        nameAdmin.setText(user.getName());
        role.setText(""+ user.getRole());
        imageView.setImage(new Image(user.getPicturePath()));
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
            FXRouter.goTo("admin_page_category", dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_category ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleUserButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_user", dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_user ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleReportButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("admin_page_report", dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า admin_page_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleResetPasswordButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("reset_password", dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า reset_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
