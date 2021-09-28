package ku.cs.models.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.models.Product;

public class ProductCard extends VBox {
    private Product product;
    private ImageView image;
    private HBox hBox;

    public ProductCard(Product product) {
        this.product = product;
        int cardWidth = 250;
        int height = cardWidth * 80 / 100;
        image = new ImageView();

        image.setFitWidth(cardWidth);
        image.setFitHeight(height);
        Image productImage = new Image(product.getPicturePath());
        PixelReader pixelReader = productImage.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader,
                (int) productImage.getWidth(),
                (int) (productImage.getHeight() * height / cardWidth));
        image.setImage(croppedImage);

        Label productNameLabel = new Label(product.getName());
        Label priceLabel = new Label("$" + String.format("%.2f", product.getPrice()));
        productNameLabel.getStyleClass().add("subtitle1");
        priceLabel.getStyleClass().add("h6");

        VBox productVbox = new VBox(productNameLabel, priceLabel);
        productVbox.setAlignment(Pos.CENTER_LEFT);
        productVbox.setSpacing(3);

        hBox = new HBox(productVbox);
        hBox.setSpacing(3);
        hBox.setPrefHeight(30);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        setPrefWidth(cardWidth);
        setAccessibleRole(AccessibleRole.BUTTON);
        setCursor(Cursor.HAND);
        getStyleClass().add("product-card");
        setId(product.getId());
        setUserData(product);
        getChildren().addAll(image, hBox);
    }

    public Product getProduct() {
        return product;
    }
}
