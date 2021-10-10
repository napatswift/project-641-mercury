package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ku.cs.models.User;
import ku.cs.models.components.dialogs.PictureConfirmDialog;
import ku.cs.service.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

public class MyAccountController {
    private DataSource dataSource;
    private User user;

    @FXML
    private ImageView imageIIV;

    @FXML
    private Label nameLabel;

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private VBox parentVBox;

    private VBox infoVBox;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        user = dataSource.getUserList().getCurrUser();
        showUser(user);
        infoVBox = (VBox) parentVBox.getChildren().get(0);
    }

    public void showUser(User user){
        usernameLabel.setText(user.getUsername());
        nameLabel.setText(user.getName());
        imageIIV.setImage(new Image(user.getPicturePath()));
        String storeName = user.getHasStore() ? user.getStoreName() : "you didn't have store";
        storeNameLabel.setText(storeName);
    }

    @FXML
    void handleResetPassword() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/reset_password.fxml"));
        try {
            Node node = loader.load();
            parentVBox.getChildren().clear();
            parentVBox.setAlignment(Pos.CENTER);
            parentVBox.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResetPasswordController controller = loader.getController();
        controller.setHandleBackButton(this::handleBack);
    }

    public void handleBack(ActionEvent event){
        parentVBox.getChildren().clear();
        parentVBox.setAlignment(Pos.TOP_CENTER);
        parentVBox.getChildren().add(infoVBox);
    }

    public void handleSelectProfilePicture() {
        FileChooser chooser = new FileChooser();

        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.jpeg"));

        File file = chooser.showOpenDialog(imageIIV.getScene().getWindow());

        if (file == null) return;

        String[] fileSplit = file.getName().split("\\.");

        File destDir = new File("images");

        if (!destDir.exists())
            if (!destDir.mkdirs()) return;

        try {
            Image uploadedImage = new Image(new FileInputStream(file.getPath()));
            PictureConfirmDialog dialog = new PictureConfirmDialog(uploadedImage);
            Optional<Boolean> result = dialog.showAndWait();

            if (result.isPresent() && result.get()) {
                String filename = LocalDate.now()
                        + "_" + System.currentTimeMillis()
                        + "." + fileSplit[fileSplit.length - 1];

                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()
                                + File.separator
                                + filename);

                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                user.setPicturePath(target.getFileName().toString());
                imageIIV.setImage(new Image(user.getPicturePath()));
                dataSource.saveAccount();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
