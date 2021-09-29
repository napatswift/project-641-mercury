package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.service.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class MyAccountController {
    DataSource dataSource;
    private File file;
    private Path target;
    private User user;



    @FXML ImageView imageIV;
    @FXML ImageView imageIIV;
    @FXML Button selectProfilePictureBtn;
    @FXML TabPane myAccountTP;
    @FXML private Label username,name;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        user = dataSource.getUserList().getCurrUser();
        imageIV.setImage(new Image(dataSource.getUserList().getCurrUser().getPicturePath()));
        showUser(user);
    }

    public void showUser(User user){
        username.setText(user.getUsername());
        name.setText(user.getName());
        imageIIV.setImage(new Image(dataSource.getUserList().getCurrUser().getPicturePath()));
    }

    public void handleResetPassword() {
        try {
            FXRouter.goTo("reset_password", dataSource);
        } catch (Exception e) {
            System.err.println("ไปที่หน้า reset_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleChangePicture() {
        myAccountTP.getSelectionModel().select(1);
    }

    public void handleConfirmBtn(ActionEvent event) throws FileNotFoundException {
        if (file != null) {
            try {
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                dataSource.getUserList().getCurrUser().setPicturePath(target.getFileName().toString());
                FXRouter.goTo("my_account", dataSource);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า my_account ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }finally {
                dataSource.saveAccount();
            }

        }
    }

    @FXML
    public void handleBackBtn(ActionEvent event){
        try {
            FXRouter.goTo("marketplace",dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleCancelBtn(ActionEvent event) {
        try {
            FXRouter.goTo("my_account", dataSource);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า my_account ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

        public void handleSelectProfilePicture(ActionEvent event) throws FileNotFoundException {

            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

            file = chooser.showOpenDialog(selectProfilePictureBtn.getScene().getWindow());

            if (file != null) {
                File destDir = new File("images");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                Image uploadedImage = new Image(new FileInputStream(file.getPath()));
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now()
                        + "_" + System.currentTimeMillis()
                        + "." + fileSplit[fileSplit.length - 1];

                target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()
                                + System.getProperty("file.separator")
                                + filename);

                imageIV.setImage(uploadedImage);
                imageIIV.setImage(uploadedImage);
            }
        }
}
