package ku.cs.models;

import java.util.ArrayList;
import java.util.List;

public class Category {
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
}
