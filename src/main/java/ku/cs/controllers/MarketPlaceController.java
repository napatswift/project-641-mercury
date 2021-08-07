package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Random;

public class MarketPlaceController {
    @FXML
    VBox gridVbox;

    private VBox getCard(String name, double price, String imagePath){
        int cardWidth = 200;
        int width = cardWidth;
        int height = width * 80 / 100;
        ImageView image = new ImageView();
        image.setFitWidth(width);
        image.setFitHeight(height);
        Image productImage = new Image(imagePath);
        PixelReader pixelReader = productImage.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader,
                (int) productImage.getWidth(),
                (int) (productImage.getHeight() * height / width ));
        image.setImage(croppedImage);

        Label productNameLabel = new Label(name);
        Label priceLabel = new Label("฿" + String.format("%.2f", price));
        productNameLabel.getStyleClass().add("subtitle1");
        priceLabel.getStyleClass().add("subtitle2");

        VBox productVbox = new VBox(productNameLabel);
        productVbox.setPrefWidth(cardWidth * .6);
        productVbox.setAlignment(Pos.CENTER_LEFT);
        VBox priceVbox = new VBox(priceLabel);
        priceVbox.setAlignment(Pos.CENTER_RIGHT);
        priceVbox.setPrefWidth(cardWidth * .4);

        HBox hBox = new HBox(productVbox, priceVbox);
        hBox.setSpacing(3);
        hBox.setPrefHeight(30);
        hBox.setStyle("-fx-background-color: app-background-color");
        hBox.setPadding(new Insets(10, 10, 10, 10));

        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(2);
        shadow.setRadius(3);
        shadow.setColor(Color.color(0,0,0,0.1));

        VBox card = new VBox(image, hBox);
        card.setPrefWidth(cardWidth);
        card.setEffect(shadow);
        card.setAccessibleRole(AccessibleRole.BUTTON);
        card.setCursor(Cursor.HAND);
        return card;
    }

    @FXML
    public void initialize() throws IOException {
        gridVbox.setPadding(new Insets(30, 0, 10, 0));
        gridVbox.setSpacing(10);
        Random n = new Random(13);
        for (int j = 0; j < 10; j++) {
            HBox row = new HBox();
            row.setSpacing(10);
            row.setAlignment(Pos.TOP_CENTER);
            for (int i = 0; i < 4; i++) {
                VBox card = getCard("Shirt",
                        n.nextDouble() * (1 + n.nextInt(10)) * 100,
                        getClass().getResource("/ku/cs/image/media-cup-holder.png").toExternalForm());
                row.getChildren().add(card);
            }
            gridVbox.getChildren().add(row);
        }
    }
}
