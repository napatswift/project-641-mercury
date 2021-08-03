package ku.cs.models;

import java.util.Collection;
import java.util.TreeSet;

public class Category implements Comparable<Category>{
    private String name;
    private Collection<SubCategory> subCategories;

    @Override
    public int compareTo(Category other) {
        return this.name.compareTo(other.getName());
    }

    public Category(String name){
        this.name = name;
        this.subCategories = new TreeSet<>();
    }

    public Category(String name, SubCategory subCategory){
        this(name);
        this.addSubCategory(subCategory);
    }

    public void addSubCategory(SubCategory subCategory){
        this.subCategories.add(subCategory);
        subCategory.setCategory(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String subCategories = " -";
        for(SubCategory subCat: this.subCategories){
            subCategories += " " + subCat.getName();
        }
        return name + subCategories;
    }
}
