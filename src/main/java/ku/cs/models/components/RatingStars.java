package ku.cs.models.components;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

public class RatingStars extends HBox {
    private double rating;
    private final String STAR_POINT = "M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z";

    public RatingStars(double rating) {
        this.rating = rating;
        for(int i = 0; i < 5; i++){
            SVGPath star = new SVGPath();
            star.setContent(STAR_POINT);
            star.setScaleX(.7); star.setScaleY(.7);
            getChildren().add(star);
        }

        updateRating();
    }

    public void setRating(double rating) {
        this.rating = rating;
        updateRating();
    }

    public double getRating() { return rating; }

    private void updateRating(){
        int i = 0;
        for(Node node: getChildren()){
            if ( (i++) + 1 <= rating){
                node.setStyle("-fx-fill: primary-color");
            } else{
                node.setStyle("-fx-fill: primary-overlay");
            }
        }
    }
}
