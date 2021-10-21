package ku.cs.models.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import ku.cs.models.Category;
import ku.cs.models.SubCategory;

public class CategoryPane extends HBox {

    public CategoryPane(Category category) {
        HBox shipHBox = new HBox();
        shipHBox.setSpacing(5);

        shipHBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        shipHBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        ScrollPane scrollPane = new ScrollPane(shipHBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Label categoryLabel = new Label(category.getName());
        categoryLabel.getStyleClass().add("subtitle1");
        shipHBox.getChildren().add(categoryLabel);
        shipHBox.setAlignment(Pos.CENTER_LEFT);

        for (SubCategory subCategory: category.getSubCategories()){
            shipHBox.getChildren().add(new Ship(subCategory.getName(), subCategory.getValue()));
        }
        getChildren().add(scrollPane);
    }
}
