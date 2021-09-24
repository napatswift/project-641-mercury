package ku.cs.models.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class Ship extends HBox {

    public Ship(String header, String value) {
        super();
        Label headerLabel = new Label(header);
        Label valueLabel = new Label(value);
        headerLabel.getStyleClass().add("subtitle2");
        headerLabel.setStyle("-fx-text-fill: on-surface-color;");
        valueLabel.getStyleClass().add("subtitle1");

        super.getChildren().addAll(headerLabel, valueLabel);

        super.setSpacing(15);
        super.setStyle("" +
                "-fx-background-radius: 40;" +
                "-fx-background-color: primary-overlay;" +
                "-fx-border-color: primary-dark-color;" +
                "-fx-text-fill: on-surface-text-med-color;" +
                "-fx-border-width: .115em;" +
                "-fx-border-radius: 40");
        setPadding(new Insets(8, 15, 8, 15));
        prefWidth(Region.USE_COMPUTED_SIZE);
        setAlignment(Pos.CENTER);
    }
}
