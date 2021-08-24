package ku.cs.models;

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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ComponentBuilder {
    public VBox productCard(String name, double price, String imagePath, String id){
        int cardWidth = 250;
        int height = cardWidth * 80 / 100;
        ImageView image = new ImageView();
        image.setFitWidth(cardWidth);
        image.setFitHeight(height);
        Image productImage = new Image(imagePath);
        PixelReader pixelReader = productImage.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader,
                (int) productImage.getWidth(),
                (int) (productImage.getHeight() * height / cardWidth));
        image.setImage(croppedImage);

        Label productNameLabel = new Label(name);
        Label priceLabel = new Label("$" + String.format("%.2f", price));
        productNameLabel.getStyleClass().add("subtitle1");
        priceLabel.getStyleClass().add("h6");

        VBox productVbox = new VBox(productNameLabel, priceLabel);
        productVbox.setAlignment(Pos.CENTER_LEFT);
        productVbox.setSpacing(3);

        HBox hBox = new HBox(productVbox);
        hBox.setSpacing(3);
        hBox.setPrefHeight(30);
        hBox.setPadding(new Insets(10, 10, 10, 10));

        VBox card = new VBox(image, hBox);
        card.setPrefWidth(cardWidth);
        card.setAccessibleRole(AccessibleRole.BUTTON);
        card.setCursor(Cursor.HAND);
        card.getStyleClass().add("product-card");
        card.setId(id);

        return card;
    }

    public HBox ship(String header, String value){
        Label headerLabel = new Label(header);
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-family: 'Roboto Medium'");
        HBox hBox = new HBox(headerLabel, valueLabel);
        hBox.setSpacing(15);
        hBox.setStyle("" +
                "-fx-background-radius: 40;" +
                "-fx-background-color: primary-overlay;" +
                "-fx-border-color: primary-dark-color;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 40");
        hBox.setPadding(new Insets(5, 15, 5, 15));
        hBox.prefWidth(Region.USE_COMPUTED_SIZE);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
}
