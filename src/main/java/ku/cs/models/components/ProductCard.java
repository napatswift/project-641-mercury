package ku.cs.models.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import ku.cs.models.Product;

public class ProductCard extends VBox {
    private Product product;
    private final VBox card;
    private final Label productNameLabel;
    private final Label priceLabel;
    private final ImageView image;
    private final int cardWidth = 250;
    private final int height = cardWidth * 80 / 100;

    public ProductCard(Product product) {
        image = new ImageView();

        image.setFitWidth(cardWidth);
        image.setPreserveRatio(true);

        productNameLabel = new Label();
        priceLabel = new Label();
        productNameLabel.getStyleClass().add("subtitle1");
        priceLabel.getStyleClass().add("h6");

        VBox productVbox = new VBox(productNameLabel, priceLabel);
        productVbox.setAlignment(Pos.CENTER_LEFT);
        productVbox.setSpacing(3);

        HBox hBox = new HBox(productVbox);
        hBox.setSpacing(3);
        hBox.setPrefHeight(30);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        card = new VBox(image, hBox);
        card.setPrefWidth(cardWidth);
        card.setAccessibleRole(AccessibleRole.BUTTON);
        card.setCursor(Cursor.HAND);
        getChildren().add(card);
        setProduct(product);
    }

    public Product getProduct() { return product; }

    public void setProduct(Product product) {
        updateData(product);
        this.product = product;
    }

    private void updateData(Product product){
        if (product != null) {
            if (!getChildren().contains(card))
                getChildren().add(card);
            if (this.product != product) {
                Image productImage = new Image(product.getPicturePath());
                PixelReader pixelReader = productImage.getPixelReader();

                int width = (int) productImage.getWidth();
                if (width > (int) productImage.getHeight())
                    width = (int) productImage.getHeight();

                WritableImage croppedImage = new WritableImage(pixelReader, width, (width * height / cardWidth));
                image.setImage(croppedImage);
            }
            getStyleClass().add("product-card");
            productNameLabel.setText(product.getName());
            priceLabel.setText("$" + String.format("%.2f", product.getPrice()));
        } else {
            getStyleClass().clear();
            getChildren().clear();
        }
    }
}
