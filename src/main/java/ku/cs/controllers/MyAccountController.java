package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.*;
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

    @FXML
    private ImageView imageIIV;

    @FXML
    private Label nameLabel;

    @FXML
    private Label usernameLabel;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        user = dataSource.getUserList().getCurrUser();
        imageIIV.setImage(new Image(user.getPicturePath()));
        showUser(user);
    }

    public void showUser(User user){
        usernameLabel.setText(user.getUsername());
        nameLabel.setText(user.getName());
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

    public void handleConfirmBtn(ActionEvent event) throws FileNotFoundException {
        if (file != null) {
            try {
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                dataSource.getUserList().getCurrUser().setPicturePath(target.getFileName().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSelectProfilePicture() throws FileNotFoundException {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

            file = chooser.showOpenDialog(usernameLabel.getScene().getWindow());

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

                imageIIV.setImage(uploadedImage);
            }
        }
}
