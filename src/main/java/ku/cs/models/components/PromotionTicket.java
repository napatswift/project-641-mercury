package ku.cs.models.components;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class PromotionTicket extends HBox {
    private final Label promotionInfoLabel;
    private final Label storeNameLabel;
    private final Label bigNumber;
    private final Label promotionDesc;
    private final Label percent;
    private final StackPane leftStackPane;
    private final StackPane rightStackPane;
    private boolean toggle;

    public PromotionTicket(double width, double height, double radius, double leftRatio) {
        getStyleClass().add("promotion-ticket");
        toggle = false;
        promotionInfoLabel = new Label("Min Spend $" + 333);
        storeNameLabel = new Label("Click to copy code");
        bigNumber = new Label("$150");
        promotionDesc = new Label("Get");
        percent = new Label("Off");

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

        VBox shopInfo = new VBox(promotionInfoLabel, storeNameLabel);
        shopInfo.setPrefWidth(width * leftRatio);
        shopInfo.setPrefHeight(height);
        shopInfo.setAlignment(Pos.CENTER);
        VBox promotionInfo = new VBox(promotionDesc, bigNumber, percent);
        promotionInfo.setPrefWidth(width * (1 - leftRatio));
        promotionInfo.setAlignment(Pos.CENTER);
        promotionInfo.setPrefHeight(height);
        leftStackPane = new StackPane(leftShape, shopInfo);
        rightStackPane = new StackPane(rightShape, promotionInfo);
        getChildren().addAll(leftStackPane, rightStackPane);
        setPrefWidth(-1);
        setPadding(new Insets(5, 10, 5, 10));

        setOnMouseClicked(this::handlePutCode);
        setOnMouseEntered(this::toggleTicketAnimation);
        setOnMouseExited(this::toggleTicketAnimation);
    }

    private void handlePutCode(MouseEvent event){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString("CODE");
        clipboard.setContent(content);
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

    public PromotionTicket() {
        this(300, 100, 0.05, 0.65);
    }

    public void updatePromotion(){
        promotionInfoLabel.setText("Min Spend $" + 333);
        storeNameLabel.setText("Store Name");

        promotionDesc.setText("Up to");
        bigNumber.setText("50");
        percent.setText("%");
    }
}
