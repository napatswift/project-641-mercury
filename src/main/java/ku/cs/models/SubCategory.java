package ku.cs.models;

public class SubCategory {
    private final String name;
    private final String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public SubCategory(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString(){
        return name + ":" + value + " ";
    }

}
