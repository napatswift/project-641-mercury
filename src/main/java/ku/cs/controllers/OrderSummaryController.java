package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import ku.cs.models.Order;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.models.User;
import ku.cs.models.coupon.CouponList;
import ku.cs.service.DataSource;

public class OrderSummaryController {
    private DataSource dataSource;
    private ProductList productList;
    private CouponList couponList;
    private Order order;
    private User buyer;
    private int amountBuy;
    private double allPayment;
    private double deCre;

    @FXML
    private Label nameProductText,
            unitPriceText,
            unitText,
            allPaymentText,
            discountText;

    @FXML
    private ImageView selectedProductImageView;

    @FXML
    private TextField couponCodeTF;

    @FXML
    private Button cancelBtn;
    private Product product;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        dataSource.parseOrder();
        dataSource.parseCoupon();
    }

    public void setAmountBuy(int amountBuy) {
        productList = dataSource.getProducts();
        this.amountBuy = amountBuy;
        allPayment = amountBuy * productList.getSelectedProduct().getPrice();
        showProduct(productList.getSelectedProduct(), amountBuy);
    }

    public void setData(){
        couponList = dataSource.getCoupons();
        buyer = dataSource.getUserList().getCurrUser();
        order = new Order(product, amountBuy, buyer);
    }

    public void showProduct(Product product, int amountBuy){
        this.product = product;
        nameProductText.setText(product.getName());

        unitPriceText.setText("$" + product.getPrice() + " per each");
        unitText.setText("x" + amountBuy);

        discountText.setVisible(false);
        allPaymentText.setText(String.format("$%.2f", allPayment - deCre));
        selectedProductImageView.setImage(new Image(product.getPicturePath()));
        setData();
    }

    public void handleOrderBtn() {
        if(productList.getSelectedProduct().sell(amountBuy)) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Your order has been placed.", ButtonType.OK);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("Thank you!");
            alert.showAndWait();
            dataSource.getOrders().addOrder(order);
            dataSource.saveOrder();
            dataSource.saveProduct();
            cancelBtn.fire();
        }
    }

    public void setOnActionCancelBtn(EventHandler<ActionEvent> eventEventHandler) {
        cancelBtn.setOnAction(eventEventHandler);
    }

    @FXML
    public void handleCouponBtn() {
        String code = couponCodeTF.getText();
        System.out.println(code +"  "+ couponList.useCoupon(code,order));
        System.out.println(order.getTotal());
        if(couponList.useCoupon(code, order) > 0){
            discountText.setVisible(true);
            allPaymentText.getStyleClass().add("discounted-label");
            discountText.setText("" + couponList.useCoupon(code, order));
        }
        else {
            discountText.setVisible(false);
            allPaymentText.getStyleClass().remove("discounted-label");
            Alert alert = new Alert(Alert.AlertType.NONE, "CouponType เกิดข้อผิดพลาด.", ButtonType.OK);
            alert.initStyle(StageStyle.UTILITY);
            alert.setHeaderText("ค่อยทำ");
            alert.showAndWait();;
        }
    }
}
