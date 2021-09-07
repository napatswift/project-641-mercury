package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import java.io.IOException;
public class HowToController {

    @FXML
    TabPane howToTP;

    @FXML
    public void handleRegisterBtn(ActionEvent event){
        howToTP.getSelectionModel().select(0);
    }

    @FXML
    public void handleHowToUseBtn(ActionEvent event){
        howToTP.getSelectionModel().select(1);
    }

    @FXML
    public void handleMarketPlaceBtn(ActionEvent event){
        howToTP.getSelectionModel().select(2);
    }

    @FXML
    public void handleSellerBtn(ActionEvent event){
        howToTP.getSelectionModel().select(3);
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


    @FXML
    private ImageView image_home_page,image_sign_up_page,image_sign_up_page_2;

    public void initialize() {
        String pathHomePage = getClass().getResource("/ku/cs/image/home-page.png").toExternalForm();
        image_home_page.setImage(new Image(pathHomePage));
        String pathSignUpPage = getClass().getResource("/ku/cs/image/sign-up-page.png").toExternalForm();
        image_sign_up_page.setImage(new Image(pathSignUpPage));
        String pathSignUpPage2 = getClass().getResource("/ku/cs/image/sign-up-page2.png").toExternalForm();
        image_sign_up_page_2.setImage(new Image(pathSignUpPage2));
    }

}
