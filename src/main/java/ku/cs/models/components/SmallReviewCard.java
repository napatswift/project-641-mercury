package ku.cs.models.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.models.Review;

public class SmallReviewCard extends VBox {
    private Review review;

    public SmallReviewCard(Review review) {
        this.review = review;

        HBox userDetail = new UserInfoCard(review.getAuthor());
        Label title = new Label(review.getTitle());
        Label detail = new Label(review.getDetail());
        HBox titleContainer = new HBox(title);
        detail.setWrapText(true);
        title.getStyleClass().add("h6");
        detail.getStyleClass().add("body1");
        getChildren().addAll(userDetail, titleContainer, detail);

        setSpacing(5.);
        setPadding(new Insets(16, 20, 16, 20));
    }
}
