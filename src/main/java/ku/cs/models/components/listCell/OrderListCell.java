package ku.cs.models.components.listCell;

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
import ku.cs.models.components.UserInfoCard;
import ku.cs.models.utils.DateTime;
import ku.cs.service.DataSource;

public class OrderListCell extends ListCell<Order> {
    private final VBox content;
    private final UserInfoCard userInfoCard;
    private final ImageView productImage;
    private final Label timeLabel;
    private final Label productNameLabel;
    private final Label amountLabel;
    private final Label totalCostLabel;
    private final VBox trackingNumberVBox;
    private final SVGPath statusSVGPath;
    private final Label trackingNumberLabel;
    private final TextField trackingNumberTextField;
    private final Button submitTrackingNumberBtn;
    private final VBox statusVBox;

    private Order order;
    private DataSource dataSource;

    public OrderListCell(DataSource dataSource) {
        this.dataSource = dataSource;
        content = new VBox();
        userInfoCard = new UserInfoCard();
        productImage = new ImageView();
        timeLabel = new Label();
        productNameLabel = new Label();
        amountLabel = new Label();
        totalCostLabel = new Label();
        trackingNumberVBox = new VBox();
        statusVBox = new VBox();
        statusSVGPath = new SVGPath();
        trackingNumberLabel = new Label();
        trackingNumberTextField = new TextField();
        submitTrackingNumberBtn = new Button();

        productImage.setPreserveRatio(true);
        productImage.setFitWidth(50);
        productImage.setFitHeight(50);
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
        productInfo.setSpacing(8);

        bottomGridPane.add(productInfo, 0, 0);
        bottomGridPane.add(amountLabel, 1, 0);
        bottomGridPane.add(totalCostLabel, 2, 0);
        bottomGridPane.add(trackingNumberVBox, 3, 0);
        bottomGridPane.add(statusVBox, 4, 0);

        content.getChildren().add(new VBox(topGridPane, bottomGridPane));

        setPadding(new Insets(0));

        trackingNumberTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("a-zA-Z\\d*")) {
                trackingNumberTextField.setText(newValue.replaceAll("[^a-zA-Z\\d]", ""));
            }
        });

        /* styling */
        topGridPane.setStyle("-fx-background-color: surface-overlay");
        statusSVGPath.setStyle("-fx-fill: on-surface-color");
        productNameLabel.getStyleClass().add("subtitle1");
        amountLabel.getStyleClass().add("overline");
        totalCostLabel.getStyleClass().add("subtitle1");
        trackingNumberLabel.getStyleClass().add("subtitle1");
        timeLabel.getStyleClass().add("overline");
    }

    private void updateOrder(Order order) {
        updateProductInfo(order);
        this.order = order;
        updateStatus();
        updateUser();
        updateTime();
    }

    private void updateUser() { userInfoCard.setUser(order.getBuyer()); }

    private void updateProductInfo(Order order){
        Product product = order.getProduct();
        if (this.order == null || product.getPicturePath() != this.order.getProduct().getPicturePath()) {
            String picturePath = product.getPicturePath();
            Image image = new Image(picturePath);
            PixelReader pixelReader = image.getPixelReader();
            WritableImage croppedImage = new WritableImage(pixelReader,
                    (int) image.getWidth(),
                    (int) image.getHeight());
            productImage.setImage(croppedImage);
        }
        productNameLabel.setText(product.getName());
        amountLabel.setText("x" + order.getQuantity());
        totalCostLabel.setText(String.format("$%.2f", product.getPrice() * order.getQuantity()));
    }

    private void updateStatus(){
        trackingNumberVBox.getChildren().clear();
        boolean shipped = order.isShipped();
        String trackingNumber = order.getTracking();

        if (shipped) {
            statusVBox.getChildren().remove(submitTrackingNumberBtn);
            submitTrackingNumberBtn.setGraphic(null);
            if(!statusVBox.getChildren().contains(statusSVGPath)){
                statusVBox.getChildren().add(statusSVGPath);
            }
            statusSVGPath.setContent("M20.7,10.7h-2V8h-1.1l1.3-1.3l-1.4-1.4L14.8,8L8,14.8l-1.1,1.1l-4.2-4.2l-1.4,1.4l5.6,5.6l2-2h0.5c0,0.4,0.1,0.8,0.4,1.2c0.4,0.5,1,0.8,1.6,0.8c1.1,0,2-0.9,2-2h4c0,1.1,0.9,2,2,2c1.1,0,2-0.9,2-2h1.3v-3.3L20.7,10.7z M11.4,17.7c-0.4,0-0.7-0.2-0.9-0.6c-0.1-0.1-0.1-0.3-0.1-0.4c0-0.6,0.4-1,1-1c0.2,0,0.3,0,0.4,0.1c0.3,0.1,0.6,0.5,0.6,0.9C12.4,17.3,11.9,17.7,11.4,17.7zM19.4,17.7c-0.6,0-1-0.5-1-1c0-0.6,0.4-1,1-1c0.6,0,1,0.4,1,1C20.4,17.3,19.9,17.7,19.4,17.7z M18.7,13.4v-1.7h1.7l1.3,1.7H18.7zM9.4,8C8.6,8,8,8.6,8,9.4v3.4L12.7,8H9.4z");
            trackingNumberLabel.setText(trackingNumber.toUpperCase());
            trackingNumberVBox.getChildren().add(trackingNumberLabel);
        } else {
            statusVBox.getChildren().add(submitTrackingNumberBtn);
            submitTrackingNumberBtn.setGraphic(statusSVGPath);
            statusSVGPath.setContent("M11.59 7.41L15.17 11H1v2h14.17l-3.59 3.59L13 18l6-6-6-6-1.41 1.41zM20 6v12h2V6h-2z");
            trackingNumberVBox.getChildren().add(trackingNumberTextField);
            submitTrackingNumberBtn.setOnAction(this::submitTrackingNumberButtonHandler);
        }
    }

    private void updateTime(){
        timeLabel.setText(DateTime.toReadableDateTime(order.getTime()));
    }

    private void submitTrackingNumberButtonHandler(ActionEvent event){
        order.setTracking(trackingNumberTextField.getText());
        updateStatus();
        updateUser();
        updateProductInfo(this.order);
        dataSource.saveOrder();
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
    public void updateSelected(boolean selected) {
        super.updateSelected(false);
    }
}