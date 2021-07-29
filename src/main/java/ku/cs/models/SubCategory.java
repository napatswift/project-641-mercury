package ku.cs.models;

public class SubCategory {
    private String name;
    private Category category;

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
}
