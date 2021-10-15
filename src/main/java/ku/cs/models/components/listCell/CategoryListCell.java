package ku.cs.models.components.listCell;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class CategoryListCell extends ListCell<String> {
    private final HBox content;
    private final Label label;

    public CategoryListCell() {
        label = new Label();
        label.getStyleClass().add("body1");
        label.setStyle("-fx-text-fill: on-surface-color");
        VBox labelVBox = new VBox(label);
        SVGPath icon = new SVGPath();
        icon.setContent("M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z");
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
