package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import java.io.IOException;

public class AboutUsController {
    @FXML
    public void handleBack(ActionEvent event){
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    private ImageView image_chang,image_feel,image_bank,image_mek;

    public void initialize() {
        String pathChang = getClass().getResource("/ku/cs/image/media-cup-holder.png").toExternalForm();
        image_chang.setImage(new Image(pathChang));
        String pathFeel = getClass().getResource("/ku/cs/image/media-cup-holder.png").toExternalForm();
        image_feel.setImage(new Image(pathFeel));
        String pathBank = getClass().getResource("/ku/cs/image/media-cup-holder.png").toExternalForm();
        image_bank.setImage(new Image(pathBank));
        String pathMek = getClass().getResource("/ku/cs/image/media-cup-holder.png").toExternalForm();
        image_mek.setImage(new Image(pathMek));
    }
}
