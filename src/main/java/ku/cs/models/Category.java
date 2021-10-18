package ku.cs.models;

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
        String result = name + ", ";
        for(SubCategory subCategory : subCategories){
            result += subCategory;
        }return result;
    }

    public List<String> toCSV(){
        List<String> list = new ArrayList<>();

        for (SubCategory subCat: subCategories){
            String sc = name + ":" + subCat.getName() + ":" + subCat.getValue();
            list.add(sc);
        }
        return list;
    }

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
