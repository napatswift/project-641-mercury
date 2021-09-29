package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import ku.cs.models.Store;
import ku.cs.models.User;

public class StoreProductPageController {

    @FXML
    private ImageView storePictureIV;

    @FXML
    private Label storeNameLabel;

    @FXML
    private FlowPane productFlowPane;

    private Store store;

    private void updateStoreInfo(){
        if (store == null) throw new NullPointerException("store is null, in Store Product Page");

        User owner = store.getOwner();
        Image storeImage = new Image(owner.getPicturePath());
        storePictureIV.setImage(storeImage);

        storeNameLabel.setText(store.getName());
    }

    public void setStore(Store store) {
        this.store = store;
        updateStoreInfo();

    }

    public FlowPane getProductFlowPane() {
        return productFlowPane;
    }
}
