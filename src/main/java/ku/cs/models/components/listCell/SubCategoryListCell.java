package ku.cs.models.components.listCell;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class SubCategoryListCell extends ListCell<String> {
    private final VBox content;
    private final Label label;

    public SubCategoryListCell() {
        label = new Label();
        label.getStyleClass().add("body1");
        label.setStyle("-fx-text-fill: on-surface-color");
        content = new VBox(label);
        content.setSpacing(20);
        content.setPadding(new Insets(10, 0, 10, 0));
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