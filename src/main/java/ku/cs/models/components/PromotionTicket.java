package ku.cs.models.components;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.util.Duration;
import ku.cs.models.coupon.Coupon;

public class PromotionTicket extends HBox {
    private Coupon coupon;
    private final Label promotionInfoLabel;
    private final Label codeLabel;
    private final Label bigNumber;
    private final StackPane rightStackPane;
    private boolean toggle;

    public PromotionTicket(Coupon coupon, double width, double height, double radius, double leftRatio) {
        setMinHeight(height);
        getStyleClass().add("promotion-ticket");
        toggle = false;
        promotionInfoLabel = new Label();
        codeLabel = new Label();
        bigNumber = new Label();
        Label promotionDesc = new Label("get");
        Label percent = new Label("off");

        Rectangle rectangle = new Rectangle(width * leftRatio, height);
        Circle circle = new Circle(width * leftRatio, 0, height * radius);
        Circle circle2 = new Circle(width * leftRatio, height, height * radius);
        Shape leftShape = Shape.subtract(rectangle, circle);
        leftShape = Shape.subtract(leftShape, circle2);

        Rectangle rectangle2 = new Rectangle(width * (1 - leftRatio), height);
        Circle circle3 = new Circle(0, 0, height * radius);
        Circle circle4 = new Circle(0, height, height * radius);
        Shape rightShape = Shape.subtract(rectangle2, circle3);
        rightShape = Shape.subtract(rightShape, circle4);

        leftShape.getStyleClass().add("left-shape");
        rightShape.getStyleClass().add("right-shape");

        bigNumber.getStyleClass().add("big-number");
        promotionInfoLabel.getStyleClass().add("promotion-info");

        VBox shopInfo = new VBox(promotionInfoLabel, codeLabel);
        shopInfo.setPrefWidth(width * leftRatio);
        shopInfo.setPrefHeight(height);
        shopInfo.setAlignment(Pos.CENTER);
        VBox promotionInfo = new VBox(promotionDesc, bigNumber, percent);
        promotionInfo.setPrefWidth(width * (1 - leftRatio));
        promotionInfo.setAlignment(Pos.CENTER);
        promotionInfo.setPrefHeight(height);
        StackPane leftStackPane = new StackPane(leftShape, shopInfo);
        rightStackPane = new StackPane(rightShape, promotionInfo);
        getChildren().addAll(leftStackPane, rightStackPane);
        setPrefWidth(-1);
        setPadding(new Insets(5, 10, 5, 10));

        setCoupon(coupon);
        setOnMouseClicked(this::handlePutCode);
        setOnMouseEntered(this::toggleTicketAnimation);
        setOnMouseExited(this::toggleTicketAnimation);
        setCursor(Cursor.HAND);
    }

    private void handlePutCode(MouseEvent event){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(coupon.getCode());
        clipboard.setContent(content);
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), codeLabel);
        transition.setFromY(0);
        transition.setToY(5);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        transition.play();
    }

    private void toggleTicketAnimation(MouseEvent mouseEvent){
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), rightStackPane);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(200), rightStackPane);
        if (toggle) {
            rotateTransition.setFromAngle(10);
            rotateTransition.setToAngle(0);

            transition.setFromX(10);
            transition.setToX(0);

            transition.setFromY(10);
            transition.setToY(0);
        } else {
            rotateTransition.setFromAngle(0);
            rotateTransition.setToAngle(10);

            transition.setFromY(0);
            transition.setToY(10);

            transition.setFromX(0);
            transition.setToX(10);
        }
        rotateTransition.play();
        transition.play();
        toggle = !toggle;
    }

    public PromotionTicket(Coupon coupon) {
        this(coupon, 300, 100, 0.05, 0.65);
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
        updateInfo();
    }

    public void updateInfo(){
        promotionInfoLabel.setText(coupon.toDescriptiveString());
        codeLabel.setText("CODE: " + coupon.getCode());
        bigNumber.setText(coupon.toNumberOffString());
    }
}
