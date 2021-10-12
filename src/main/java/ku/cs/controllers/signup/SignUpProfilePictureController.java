package ku.cs.controllers.signup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.User;
import ku.cs.models.UserList;
import com.github.saacsos.FXRouter;
import ku.cs.models.utils.ImageUploader;
import ku.cs.service.DataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SignUpProfilePictureController {
    private DataSource dataSource;
    private User currUser;
    private UserList userList;
    private ImageUploader imageUploader;

    @FXML
    Button selectProfilePictureBtn;

    @FXML
    ImageView pictureViewIV;

    public void initialize() {
        Object[] data = (Object[]) FXRouter.getData();
        dataSource = (DataSource) data[1];
        userList = dataSource.getUserList();
        currUser = (User) data[0];
    }

    @FXML
    public void handleConfirmBtn(ActionEvent event) {
        try {
            imageUploader.saveImageFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (userList.addUser(currUser)){
            dataSource.saveAccount();
            if (this.userList == null) return;

            try {
                FXRouter.goTo("login");
            } catch (IOException ignore) {
            }
        }
    }

    @FXML
    public void handleSelectProfilePicture(ActionEvent event) throws FileNotFoundException {
        imageUploader = new ImageUploader(pictureViewIV.getScene().getWindow(), "images");
        imageUploader.show();
        pictureViewIV.setImage(new Image(new FileInputStream(imageUploader.getUploadedFile())));
    }

    @FXML
    public void handleBack(ActionEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
