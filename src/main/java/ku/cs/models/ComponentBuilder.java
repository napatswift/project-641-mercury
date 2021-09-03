package ku.cs.models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

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

    public ScrollPane categoryPane(Category category){
        HBox shipHBox = new HBox();
        shipHBox.setSpacing(5);
        shipHBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        shipHBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        ScrollPane scrollPane = new ScrollPane(shipHBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Label categoryLabel = new Label(category.getName());
        categoryLabel.getStyleClass().add("subtitle1");
        shipHBox.getChildren().add(categoryLabel);
        for (SubCategory subCategory: category.getSubCategories()){
            shipHBox.getChildren().add(ship(subCategory.getName(), subCategory.getValue()));
        }
        return scrollPane;
    }


    public VBox reviewCard(Review review){

        HBox starsHBox = new HBox();
        starsRating(starsHBox, review.getRating());
        Label ratingLabel = new Label(review.getRating() + "/5");
        ratingLabel.setPadding(new Insets(0, 0, 0, 10));
        starsHBox.getChildren().add(ratingLabel);
        starsHBox.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label(review.getTitle());
        titleLabel.getStyleClass().add("h6");
        Label detailLabel = new Label(review.getDetail());
        detailLabel.getStyleClass().add("body1");
        detailLabel.setWrapText(true);
        Label usernameLabel = new Label(review.getReviewerUsername());
        usernameLabel.getStyleClass().add("subtitle2");
        VBox card = new VBox(starsHBox, titleLabel, detailLabel, usernameLabel);
        card.setSpacing(5);
        card.setPadding(new Insets(26, 33, 26, 33));
        card.getStyleClass().add("review-card");
        return card;
    }

    public static void starsRating(HBox starsHBox, double rating) {
        for(int i = 0; i < 5; i++){
            SVGPath star = new SVGPath();
            star.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
            if ( i+1 <= rating){
                star.setStyle("-fx-fill: primary-color");
            } else{
                star.setStyle("-fx-fill: primary-overlay");
            }
            star.setScaleX(.7); star.setScaleY(.7);
            starsHBox.getChildren().add(star);
        }
    }
}
