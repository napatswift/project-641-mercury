package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import java.io.IOException;
public class HowToController {
    @FXML
    public void handleBack(ActionEvent event) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleHowToUse(ActionEvent event){
        try {
            com.github.saacsos.FXRouter.goTo("how_to_use");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า HowToUse ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleMarket(ActionEvent event){
        try {
            com.github.saacsos.FXRouter.goTo("how_to_market");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า HowToMarket ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSeller(ActionEvent event){
        try {
            com.github.saacsos.FXRouter.goTo("how_to_seller");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า HowToSeller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleRegister(ActionEvent event){
        try {
            com.github.saacsos.FXRouter.goTo("how_to_register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า HowToSeller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
