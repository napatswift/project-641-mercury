package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ku.cs.App;

import java.io.IOException;

public class SignUpController {

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
