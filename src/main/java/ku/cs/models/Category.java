package ku.cs.models;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Category implements Comparable<Category>{
    private String name;
    private final List<SubCategory> subCategories;

    public Category(String name) {
        this.name = name;
        subCategories = new ArrayList<>();
    }

    public void addSubCategory(SubCategory subCategory){
        subCategories.add(subCategory);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add("name='" + name + "'")
                .add("subCategories=" + subCategories)
                .toString();
    }

    public List<String> toCSV(){
        List<String> list = new ArrayList<>();

        for (SubCategory subCat: subCategories){
            String sc = name + ":" + subCat.getName() + ":" + subCat.getValue();
            list.add(sc);
        }

        return list;
    }

    public static class CategoryListCell extends ListCell<String> {
        private final VBox content;
        private final Label label;

        public CategoryListCell() {
            label = new Label();
            label.getStyleClass().add("body1");
            label.setStyle("-fx-text-fill: on-surface-color");
            content = new VBox(label);
            content.setSpacing(3);
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

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
