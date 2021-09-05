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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import ku.cs.controllers.MarketPlaceController;

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
        hBox.setPadding(new Insets(8, 15, 8, 15));
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
        shipHBox.setAlignment(Pos.CENTER_LEFT);
        for (SubCategory subCategory: category.getSubCategories()){
            shipHBox.getChildren().add(ship(subCategory.getName(), subCategory.getValue()));
        }
        return scrollPane;
    }

    public AnchorPane reviewCard(Review review, MarketPlaceController controller){
        SVGPath reportFlag = new SVGPath();
        reportFlag.setContent("M14.4 6L14 4H5v17h2v-7h5.6l.4 2h7V6z");
        HBox reportFlagContainer = new HBox(reportFlag);
        reportFlagContainer.setAlignment(Pos.CENTER_RIGHT);
        reportFlagContainer.setId(review.getId());
        reportFlagContainer.setOnMouseReleased(controller::handleReportReviewBtn);
        reportFlagContainer.setCursor(Cursor.HAND);

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

        ImageView writerProfilePic = new ImageView(new Image(review.getAuthor().getPicturePath()));
        writerProfilePic.setFitHeight(18);
        writerProfilePic.setPreserveRatio(true);
        writerProfilePic.setClip(new Circle(9, 9, 9));
        Label usernameLabel = new Label(review.getReviewerUsername());
        usernameLabel.getStyleClass().add("subtitle2");
        HBox userDetail = new HBox(writerProfilePic, usernameLabel);
        userDetail.setSpacing(7);

        VBox card = new VBox(starsHBox, titleLabel, detailLabel, userDetail);
        card.setSpacing(10);
        card.setPadding(new Insets(26, 33, 26, 33));
        card.getStyleClass().add("review-card");

        AnchorPane cardAnc = new AnchorPane(card, reportFlagContainer);

        AnchorPane.setRightAnchor(card, 0.);
        AnchorPane.setLeftAnchor(card, 0.);

        AnchorPane.setTopAnchor(reportFlagContainer, 33.);
        AnchorPane.setRightAnchor(reportFlagContainer, 26.);
        return cardAnc;
    }

    public void starsRating(HBox starsHBox, double rating) {
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

    public HBox productCard(Product product){
       HBox card = new HBox();
       card.setPadding(new Insets(5));
       card.setSpacing(5);

       card.getStyleClass().add("review-card");
       ImageView imageView = new ImageView(new Image(product.getPicturePath()));
       imageView.setPreserveRatio(true);
       imageView.setFitHeight(120);

       Label title = new Label(product.getName());
       title.setWrapText(true);
       title.getStyleClass().add("h6");

       String detailString = product.getDetails().substring(0, 85).trim() + "...";
       Label detail = new Label(detailString);
       detail.setWrapText(true);
       detail.getStyleClass().add("body1");

       VBox info = new VBox(title, detail);
       card.getChildren().addAll(imageView, info);
       return card;
    }

}
