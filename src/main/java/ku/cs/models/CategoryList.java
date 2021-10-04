package ku.cs.models;

import java.util.*;

public class CategoryList {
    private Map<String, ArrayList<String>> categories;

    public CategoryList() {
        categories = new HashMap<>();
    }

    public boolean containsKey(String category){
        return categories.containsKey(category);
    }

    public ArrayList<String> getSubcategoryOf(String key){
        return categories.get(key);
    }

    public ArrayList<String> put(String key, ArrayList<String> val){
        return categories.put(key, val);
    }

    public Set<String> categorySet(){
        return categories.keySet();
    }

    public String toCsv(){
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("category,subcategory");
        for(String key: categories.keySet()) {
            for (String val : categories.get(key))
                stringJoiner.add(key + "," + val);
        }
        return stringJoiner + "\n";
    }
}
