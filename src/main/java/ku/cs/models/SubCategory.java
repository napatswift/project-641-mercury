package ku.cs.models;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class SubCategory {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SubCategory(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static class SubCategoryListCell extends ListCell<String> {
        private final VBox content;
        private final Label label;

        public SubCategoryListCell() {
            label = new Label();
            label.getStyleClass().add("body1");
            label.setStyle("-fx-text-fill: on-surface-color");
            content = new VBox(label);
            content.setSpacing(20);
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

}
