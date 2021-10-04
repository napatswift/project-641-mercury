package ku.cs.models.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import ku.cs.models.Review;

public class ReviewCard extends VBox {
    private Review review;
    private HBox flagArea;

    public ReviewCard(Review review) {
        this.review = review;

        SVGPath reportFlag = new SVGPath();
        reportFlag.setContent("M14.4 6L14 4H5v17h2v-7h5.6l.4 2h7V6z");
        reportFlag.setScaleY(.8);
        reportFlag.setScaleX(.8);
        reportFlag.setStyle("-fx-fill: error-color");
        flagArea = new HBox(reportFlag);

        HBox reportFlagContainer = new HBox(flagArea);
        reportFlagContainer.setAlignment(Pos.CENTER_RIGHT);
        flagArea.setId(review.getId());
        flagArea.setCursor(Cursor.HAND);

        HBox starsHBox = new RatingStars(review.getRating());
        Label ratingLabel = new Label(review.getRating() + "/5");
        ratingLabel.setPadding(new Insets(0, 0, 0, 10));
        starsHBox.getChildren().add(ratingLabel);
        starsHBox.setAlignment(Pos.CENTER_LEFT);

        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.add(starsHBox, 0, 0);
        gridPane.add(reportFlagContainer, 1, 0);

        GridPane.setHalignment(reportFlagContainer, HPos.RIGHT);

        GridPane test = new GridPane();

        Label titleLabel = new Label(review.getTitle());
        titleLabel.getStyleClass().add("h6");
        Label detailLabel = new Label(review.getDetail());
        detailLabel.getStyleClass().add("body1");
        detailLabel.setWrapText(true);

        HBox userDetail = new UserInfoCard(review.getAuthor());
        userDetail.setSpacing(7);

        getChildren().addAll(test, gridPane, titleLabel, detailLabel, userDetail);
        setSpacing(10);
        setPadding(new Insets(26, 33, 26, 33));
        getStyleClass().add("review-card");
    }

    public HBox getFlagArea() {
        return flagArea;
    }

    public Review getReview() {
        return review;
    }
}
