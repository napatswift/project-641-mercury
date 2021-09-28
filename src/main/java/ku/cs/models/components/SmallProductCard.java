package ku.cs.models.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.models.Product;

public class SmallProductCard extends HBox {
    private Product product;

    public SmallProductCard(Product product) {
        this.product = product;

        setPadding(new Insets(5));
        setSpacing(5);

        ImageView imageView = new ImageView(new Image(product.getPicturePath()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(120);

        Label title = new Label(product.getName());
        title.setWrapText(true);
        title.getStyleClass().add("h6");
        String detailString;
        if (product.getDetails().length() > 100)
            detailString = product.getDetails().substring(0, 85).trim() + "...";
        else
            detailString = product.getDetails();
        Label detail = new Label(detailString);
        detail.setWrapText(true);
        detail.getStyleClass().add("body1");

        VBox info = new VBox(title, detail);
        getChildren().addAll(imageView, info);
    }
}
