package ku.cs.models.components;

import javafx.scene.layout.VBox;
import ku.cs.models.Category;

import java.util.ArrayList;

public class CategoryListPane extends VBox {
    private ArrayList<Category> categoryList;

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
        updateCategoryList();
    }

    private void updateCategoryList(){
        getChildren().clear();
        for(Category category: categoryList)
            getChildren().add(new CategoryPane(category));
    }
}
