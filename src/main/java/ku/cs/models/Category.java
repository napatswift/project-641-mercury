package ku.cs.models;

import java.util.Collection;

public class Category {
    private String name;
    private Collection<SubCategory> subCategories;

    public Category(String name){
        this.name = name;
    }

    public Category(String name, SubCategory subCategory){
        this(name);
        this.subCategories.add(subCategory);
        subCategory.setCategory(this);
    }

    public boolean addSubCategory(SubCategory subCategory){
        for(SubCategory subCat: this.subCategories){
            if(subCat.getName().equals(subCategory.getName())){
                return false;
            }
        }
        this.subCategories.add(subCategory);
        subCategory.setCategory(this);
        return true;
    }

    public String getName() {
        return name;
    }
}
