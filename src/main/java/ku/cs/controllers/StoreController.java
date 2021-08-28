package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import ku.cs.service.DataSource;

import java.io.IOException;

public class StoreController {
    private DataSource dataSource;

    @FXML
    public void handleBackToMarket(ActionEvent event) {
        try {
            FXRouter.goTo("marketplace");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
