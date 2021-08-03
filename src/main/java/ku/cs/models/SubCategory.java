package ku.cs.models;

import java.util.StringJoiner;

public class SubCategory implements Comparable<SubCategory>{
    private String name;
    private String detail;
    private Category category;

    @Override
    public int compareTo(SubCategory other) {
        return this.name.compareTo(other.getName());
    }

    public SubCategory(String name, String detail, Category category){
        this(name);
        this.detail = detail;
        this.category = category;
    }

    public SubCategory(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SubCategory.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("detail='" + detail + "'")
                .add("category=" + category)
                .toString();
    }

    public String toCsv(){
        /*
         * name,detail,category
         */
        return name + "," + category.getName();
    }
}
