package ku.cs.models.components;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import ku.cs.models.Order;
import ku.cs.models.Product;
import ku.cs.models.User;

public class OrderListCell extends ListCell<Order> {
    private final VBox content;
    private final UserInfoCard userInfoCard;
    private final ImageView productImage;
    private final Label timeLabel;
    private final Label productNameLabel;
    private final Label amount;
    private final Label totalCostLabel;
    private final VBox trackingNumberVBox;
    private final SVGPath statusSVGPath;
    private final Label trackingNumberLabel;
    private final TextField trackingNumberTextField;
    private final Button submitTrackingNumberBtn;
    private final VBox statusVBox;

    private Order order;

    public OrderListCell() {
        content = new VBox();
        userInfoCard = new UserInfoCard();
        productImage = new ImageView();
        timeLabel = new Label("19:01 - 25Jul");
        productNameLabel = new Label("Product Name");
        amount = new Label("x13");
        totalCostLabel = new Label("$1499");
        trackingNumberVBox = new VBox();
        statusVBox = new VBox();
        statusSVGPath = new SVGPath();
        trackingNumberLabel = new Label();
        trackingNumberTextField = new TextField();
        submitTrackingNumberBtn = new Button();

        productImage.setPreserveRatio(true);
        productImage.setFitWidth(40);
        productImage.setFitHeight(40);
        submitTrackingNumberBtn.setGraphic(statusSVGPath);
        trackingNumberVBox.setAlignment(Pos.CENTER_LEFT);
        statusVBox.setAlignment(Pos.CENTER);
        productNameLabel.setWrapText(true);
        trackingNumberTextField.setPromptText("Enter Tracking Number");

        GridPane topGridPane = new GridPane();
        ColumnConstraints tCol1 = new ColumnConstraints(); tCol1.setPercentWidth(50);
        ColumnConstraints tCol2 = new ColumnConstraints(); tCol2.setPercentWidth(50);
        tCol2.setHalignment(HPos.RIGHT);
        topGridPane.getColumnConstraints().addAll(tCol1, tCol2);
        topGridPane.add(userInfoCard, 0, 0);
        topGridPane.add(timeLabel, 1, 0);
        topGridPane.setPadding(new Insets(5, 15, 5, 15));

        GridPane bottomGridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(35);
        col1.setMaxWidth(0);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(15);
        ColumnConstraints col3 = new ColumnConstraints(); col3.setPercentWidth(15);
        ColumnConstraints col4 = new ColumnConstraints(); col4.setPercentWidth(25);
        ColumnConstraints col5 = new ColumnConstraints(); col5.setPercentWidth(10);
        col5.setHalignment(HPos.CENTER);
        bottomGridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        bottomGridPane.setPadding(new Insets(0, 5, 0, 15));
        bottomGridPane.setPrefHeight(70);
        bottomGridPane.setAlignment(Pos.CENTER_LEFT);
        bottomGridPane.setHgap(10);

        HBox productInfo = new HBox(productImage, productNameLabel);
        productInfo.setAlignment(Pos.CENTER_LEFT);

        bottomGridPane.add(productInfo, 0, 0);
        bottomGridPane.add(amount, 1, 0);
        bottomGridPane.add(totalCostLabel, 2, 0);
        bottomGridPane.add(trackingNumberVBox, 3, 0);
        bottomGridPane.add(statusVBox, 4, 0);

        content.getChildren().add(new VBox(topGridPane, bottomGridPane));

        setPadding(new Insets(0));

        /* styling */
        topGridPane.setStyle("-fx-background-color: surface-overlay");
        productNameLabel.getStyleClass().add("subtitle1");
        amount.getStyleClass().add("overline");
        totalCostLabel.getStyleClass().add("subtitle1");
        trackingNumberLabel.getStyleClass().add("subtitle1");
        timeLabel.getStyleClass().add("overline");
    }

    private void updateOrder(Order order) {
        this.order = order;
        updateStatus();
        updateUser();
        updateProductInfo();
    }
    private void updateUser() { userInfoCard.setUser(new User("username", "Name")); }

    private void updateProductInfo(){
        Product product = null;
        if (product == null) return;
        Image image = new Image(product.getPicturePath());
        PixelReader pixelReader = image.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader,
                (int) image.getWidth(),
                (int) image.getWidth());
        productImage.setImage(croppedImage);
        productNameLabel.setText(product.getName());
        totalCostLabel.setText(String.format("$%.2f", product.getPrice() * order.getAmount()));
    }

    private void updateStatus(){
        trackingNumberVBox.getChildren().clear();
        boolean shipped = order.getStatus().equals("sent");
        String trackingNumber = order.getTracking();

        if (shipped) {
            statusVBox.getChildren().remove(submitTrackingNumberBtn);
            statusVBox.getChildren().add(statusSVGPath);
            submitTrackingNumberBtn.setGraphic(null);

            statusSVGPath.setContent("M20 8h-3V4H3c-1.1 0-2 .9-2 2v11h2c0 1.66 1.34 3 3 3s3-1.34 3-3h6c0 1.66 1.34 3 3 3s3-1.34 3-3h2v-5l-3-4zM6 18.5c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5zm13.5-9l1.96 2.5H17V9.5h2.5zm-1.5 9c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5z");
            trackingNumberLabel.setText(trackingNumber);
            trackingNumberVBox.getChildren().add(trackingNumberLabel);
        } else {

            statusVBox.getChildren().add(submitTrackingNumberBtn);
            submitTrackingNumberBtn.setGraphic(statusSVGPath);
            statusSVGPath.setContent("M11.59 7.41L15.17 11H1v2h14.17l-3.59 3.59L13 18l6-6-6-6-1.41 1.41zM20 6v12h2V6h-2z");
            trackingNumberVBox.getChildren().add(trackingNumberTextField);
            submitTrackingNumberBtn.setOnAction(this::setTrackingNumber);
        }
    }

    private void setTrackingNumber(ActionEvent event){
        order.setTracking(trackingNumberTextField.getText());
        updateStatus();
        updateUser();
        updateProductInfo();
    }

    @Override
    protected void updateItem(Order item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setGraphic(content);
            updateOrder(item);
        } else {
            setGraphic(null);
            setStyle(null);
        }
    }

    @Override
    public void updateSelected(boolean selected) { super.updateSelected(false); }
}
