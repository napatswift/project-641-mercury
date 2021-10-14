package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.models.Store;
import ku.cs.models.User;
import ku.cs.models.components.PromotionTicket;
import ku.cs.models.coupon.Coupon;
import ku.cs.models.coupon.CouponList;

import java.util.List;

public class StoreProductPageController {

    @FXML
    private ImageView storePictureIV;

    @FXML
    private Label storeNameLabel;

    @FXML
    private FlowPane productFlowPane;

    @FXML
    private VBox containerVBox;

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

    public void setCouponList(CouponList couponList){
        List<Coupon> couponsByStore = couponList.toListCouponInStore(store);
        HBox couponHBox = new HBox();
        ScrollPane scrollPane = new ScrollPane(couponHBox);
        scrollPane.setMinViewportHeight(120);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        containerVBox.getChildren().add(0, scrollPane);
        for (Coupon coupon: couponsByStore) {
            couponHBox.getChildren().add(new PromotionTicket(coupon));
        }
    }

    public FlowPane getProductFlowPane() {
        return productFlowPane;
    }
}
