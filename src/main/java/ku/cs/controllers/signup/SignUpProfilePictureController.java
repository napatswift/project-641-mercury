package ku.cs.controllers.signup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.models.AccountList;
import ku.cs.models.User;
import com.github.saacsos.FXRouter;

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
    private AccountList accountList;
    private File file;
    private Path target;
    private String prevView;

    @FXML
    Button selectProfilePictureBtn;

    @FXML
    ImageView pictureViewIV;

    public void initialize() {
        Object [] data = (Object[]) FXRouter.getData();
        this.currUser = (User) data[0];
        this.accountList = (AccountList) data[1];
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

        if (accountList.addAccount(currUser)){
            accountList.toCsv("data/accounts.csv");
            if (this.accountList == null) {
                return;
            }
            FXRouter.goTo("login");
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
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
