package ku.cs.controllers.signup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.App;
import ku.cs.controllers.LoginController;
import ku.cs.models.Accounts;
import ku.cs.models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class SignUpProfilePictureController {

    private User currUser;
    private Accounts accounts;
    private File file;
    private Path target;
    private String prevView;

    @FXML
    Button selectProfilePictureBtn;

    @FXML
    ImageView pictureViewIV;

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    public void setPrevView(String prevView) {
        this.prevView = prevView;
    }

    public String getPrevView() {
        return (prevView == null ? "login.fxml" : prevView);
    }

    public void handleConfirmBtn(ActionEvent event) throws IOException{
        if (file != null) {
            try {
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                currUser.setPicturePath(target.getFileName().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (accounts.addAccount(currUser)){
            accounts.toCsv("data/users.csv");
            Button confirmBtn = (Button) event.getSource();

            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();

            if (this.accounts == null) {
                return;
            }

            LoginController loginController = loader.getController();

            loginController.setAccounts(this.accounts);

            Stage stage = (Stage) confirmBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 450, 700));
            stage.show();
        }
    }

    public void handleSelectProfilePicture(ActionEvent event) throws FileNotFoundException {

        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

        file = chooser.showOpenDialog(selectProfilePictureBtn.getScene().getWindow());

        if (file != null){
            File destDir = new File("images");
            destDir.mkdirs();

            Image uploadedImage = new Image(new FileInputStream(file.getPath()));
            String[] fileSplit = file.getName().split("\\.");
            String filename = LocalDate.now()
                    + "_" + System.currentTimeMillis()
                    + "." + fileSplit[fileSplit.length - 1];

            target = FileSystems.getDefault().getPath(
                    destDir.getAbsolutePath()
                            + System.getProperty("file.separator")
                            + filename);

            pictureViewIV.setImage(uploadedImage);
        }
    }

    public void handleBack(ActionEvent event) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
