package ku.cs.models.components.listCell;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class SubCategoryListCell extends ListCell<String> {
    private final HBox content;
    private final Label label;

    public SubCategoryListCell() {
        label = new Label();
        label.getStyleClass().add("body1");
        label.setStyle("-fx-text-fill: on-surface-color");
        VBox labelVBox = new VBox(label);
        SVGPath icon = new SVGPath();
        icon.setContent("M16.5 3c-1.74 0-3.41.81-4.5 2.09C10.91 3.81 9.24 3 7.5 3 4.42 3 2 5.42 2 8.5c0 3.78 3.4 6.86 8.55 11.54L12 21.35l1.45-1.32C18.6 15.36 22 12.28 22 8.5 22 5.42 19.58 3 16.5 3zm-4.4 15.55l-.1.1-.1-.1C7.14 14.24 4 11.39 4 8.5 4 6.5 5.5 5 7.5 5c1.54 0 3.04.99 3.57 2.36h1.87C13.46 5.99 14.96 5 16.5 5c2 0 3.5 1.5 3.5 3.5 0 2.89-3.14 5.74-7.9 10.05z");
        icon.setStyle("-fx-fill: primary-med-overlay");
        content = new HBox(icon, labelVBox);
        content.setSpacing(15);
        content.setPadding(new Insets(10, 0, 10, 10));
        content.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            label.setText(item);
            setGraphic(content);
            setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: surface-overlay");
        } else {
            setGraphic(null);
            setStyle(null);
        }
    }
}