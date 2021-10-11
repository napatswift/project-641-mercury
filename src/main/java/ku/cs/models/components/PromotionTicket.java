package ku.cs.models.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;

public class PromotionTicket extends AnchorPane {
    private final Label promotionInfoLabel;
    private final Label storeNameLabel;
    private final Label bigNumber;
    private final Label promotionDesc;
    private final Label percent;

    public PromotionTicket(double width, double height, double radius, double leftRatio) {
        getStyleClass().add("promotion-ticket");

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

        promotionInfoLabel = new Label("Min Spend $" + 333);
        storeNameLabel = new Label("Store Name");
        bigNumber = new Label("$150");
        promotionDesc = new Label("Up to");
        percent = new Label("Off");

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
        HBox container = new HBox(shopInfo, promotionInfo);

        AnchorPane.setLeftAnchor(rightShape, width * leftRatio);
        getChildren().addAll(leftShape, rightShape, container);
        setPrefWidth(-1);
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
