package ku.cs.models.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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


    private Order order;

    public OrderListCell() {
        content = new VBox();
        userInfoCard = new UserInfoCard();
        productImage = new ImageView();
        timeLabel = new Label("19:01 - 25Jul");
        productNameLabel = new Label("productName");
        amount = new Label("x13");
        totalCostLabel = new Label("$1499");
        trackingNumberVBox = new VBox();
        statusSVGPath = new SVGPath();

        setPadding(new Insets(0));
        trackingNumberVBox.setAlignment(Pos.CENTER_LEFT);
        productNameLabel.setWrapText(true);

        GridPane topGridPane = new GridPane();
        ColumnConstraints tCol1 = new ColumnConstraints();
        tCol1.setPercentWidth(50);
        ColumnConstraints tCol2 = new ColumnConstraints();
        tCol2.setPercentWidth(50);
        tCol2.setHalignment(HPos.RIGHT);
        topGridPane.getColumnConstraints().addAll(tCol1, tCol2);
        topGridPane.add(userInfoCard, 0, 0);
        topGridPane.add(timeLabel, 1, 0);
        topGridPane.setStyle("-fx-background-color: surface-overlay");
        topGridPane.setPadding(new Insets(5));

        GridPane bottomGridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(35);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(15);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(15);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(10);
        col5.setHalignment(HPos.RIGHT);
        bottomGridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        bottomGridPane.setPadding(new Insets(0, 5, 0, 5));

        HBox productInfo = new HBox(productImage, productNameLabel);
        productInfo.setAlignment(Pos.CENTER_LEFT);

        bottomGridPane.add(productInfo, 0, 0);
        bottomGridPane.add(amount, 1, 0);
        bottomGridPane.add(totalCostLabel, 2, 0);
        bottomGridPane.add(trackingNumberVBox, 3, 0);
        bottomGridPane.add(statusSVGPath, 4, 0);

        content.getChildren().add(new VBox(topGridPane, bottomGridPane));

        productImage.setPreserveRatio(true);
        productImage.setFitWidth(40);
        productImage.setFitHeight(40);
    }

    private void updateOrder(Order order){
        this.order = order;
    }

    private void updateProductInfo(Product product){
        productImage.setImage(null);
        Image image = new Image(product.getPicturePath());
        PixelReader pixelReader = image.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader,
                (int) image.getWidth(),
                (int) image.getWidth());
        productImage.setImage(croppedImage);
    }

    private void updateUser(User user){
        userInfoCard.setUser(user);
    }

    private void updateStatus(boolean shipped, String trackingNumber){
        trackingNumberVBox.getChildren().clear();
        if (shipped) {
            statusSVGPath.setContent("M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm-2 16l-4-4 1.41-1.41L10 14.17l6.59-6.59L18 9l-8 8z");
            trackingNumberVBox.getChildren().add(new Label(trackingNumber));
        } else {
            statusSVGPath.setContent("M11.59 7.41L15.17 11H1v2h14.17l-3.59 3.59L13 18l6-6-6-6-1.41 1.41zM20 6v12h2V6h-2z");
            trackingNumberVBox.getChildren().add(new TextField());
        }
    }

    @Override
    protected void updateItem(Order item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setGraphic(content);
            updateOrder(item);
            updateStatus(item.isSend(), "52v66ov7rae8402");
            updateUser(new User("userName", "Name"));
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
