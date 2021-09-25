package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
    private Text nameProductText,
            unitPriceText,
            unitText,
            allPaymentText;
    @FXML
    private ImageView selectedProductImageView;


    public void initialize() {
        Object[] data = (Object[]) FXRouter.getData();
        dataSource = (DataSource) data[0];
        amountBuy = (int) data[1];
        productList = dataSource.getProducts();

        showProduct(productList.getSelectedProduct(),amountBuy);
    }

    public void showProduct(Product product,int amountBuy){
        nameProductText.setText(product.getName());
        unitPriceText.setText(""+product.getPrice());
        unitText.setText(""+amountBuy);
        allPaymentText.setText("" + (amountBuy*product.getPrice()));
        selectedProductImageView.setImage(new Image(product.getPicturePath()));
    }

    public void handleOrderBtn(ActionEvent actionEvent) {
        if(productList.getSelectedProduct().sell(amountBuy)) {
            Alert alert = new Alert(Alert.AlertType.NONE,"กรุณารอรัยสิ้นค้าของคุณ ณ ปลายทาง", ButtonType.OK);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("การซื้อขายสำเร็จ");
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

    public void handleCancelBtn(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("marketplace", dataSource);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
