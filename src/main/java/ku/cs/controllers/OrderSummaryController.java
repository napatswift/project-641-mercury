package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.service.DataSource;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class OrderSummaryController {
    private DataSource dataSource;
    private ProductList productList;
    private int amountBuy;

    @FXML
    private Label nameProductText,
            unitPriceText,
            unitText,
            allPaymentText;
    @FXML
    private ImageView selectedProductImageView;

    @FXML
    private Button cancelBtn;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setAmountBuy(int amountBuy) {
        this.amountBuy = amountBuy;
        productList = dataSource.getProducts();
        showProduct(productList.getSelectedProduct(),amountBuy);
    }

    public void showProduct(Product product, int amountBuy){
        nameProductText.setText(product.getName());

        unitPriceText.setText("$" + product.getPrice() + " per each");
        unitText.setText("x" + amountBuy);

        allPaymentText.setText(String.format("$%.2f", amountBuy * product.getPrice()));
        selectedProductImageView.setImage(new Image(product.getPicturePath()));
    }

    public void handleOrderBtn() {
        if(productList.getSelectedProduct().sell(amountBuy)) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Your order has been placed.", ButtonType.OK);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("Thank you!");
            alert.showAndWait();
            try {
                dataSource.saveProduct();
                FXRouter.goTo("marketplace", dataSource);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    public void setOnActionCancelBtn(EventHandler<ActionEvent> eventEventHandler) {
        cancelBtn.setOnAction(eventEventHandler);
    }
}
