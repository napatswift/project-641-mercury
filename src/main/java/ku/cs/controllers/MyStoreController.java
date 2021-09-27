package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.service.DataSource;
import java.io.IOException;

public class MyStoreController  {
    DataSource dataSource;
    @FXML Label usernameLabel;
    @FXML Label nameLabel;
    @FXML Label nameStoreLabel;
    @FXML TabPane myStoreTP;
    @FXML ImageView userImage;

    public void initialize() {
        dataSource = (DataSource) FXRouter.getData();
        usernameLabel.setText(dataSource.getUserList().getCurrUser().getUsername());
        nameStoreLabel.setText(dataSource.getUserList().getCurrUser().getStoreName());
        nameLabel.setText(dataSource.getUserList().getCurrUser().getName());
        userImage.setImage(new Image(dataSource.getUserList().getCurrUser().getPicturePath()));
    }

    @FXML
    public void handleBackBtn(ActionEvent event){
        try {
            FXRouter.goTo("marketplace",dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddProductBtn(ActionEvent event) { myStoreTP.getSelectionModel().select(0); }
    @FXML
    public void handleListProductBtn(ActionEvent event){
        myStoreTP.getSelectionModel().select(1);
    }
}
