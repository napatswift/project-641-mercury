package ku.cs.models.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class Ship extends HBox {

    public Ship(String header, String value) {
        super();
        super.setStyle("-fx-background-color: black");
        Label headerLabel = new Label(header);
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-family: 'Roboto Medium'");

        super.getChildren().addAll(headerLabel, valueLabel);

        super.setSpacing(15);
        super.setStyle("" +
                "-fx-background-radius: 40;" +
                "-fx-background-color: primary-overlay;" +
                "-fx-border-color: primary-dark-color;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 40");
        setPadding(new Insets(8, 15, 8, 15));
        prefWidth(Region.USE_COMPUTED_SIZE);
        setAlignment(Pos.CENTER);
    }
}
