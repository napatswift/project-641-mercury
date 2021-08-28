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
        return new StringJoiner(", ")
                .add("name='" + name + "'")
                .add("subCategories=" + subCategories)
                .toString();
    }

    public String toTsv(){
        StringJoiner stringJoiner = new StringJoiner("\t");
        for (SubCategory subCat: subCategories){
            String sc = name + ":" + subCat.getName() + ":" + subCat.getValue();
            stringJoiner.add(sc);
        }
        return stringJoiner.toString();
    }

    @Override
    public int compareTo(Category o) {
        return this.name.compareTo(o.name);
    }
}
