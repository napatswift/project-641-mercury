package ku.cs.models;

import java.util.*;

public class CategoryList {
    private final Map<String, Set<String>> categories;

    public CategoryList() {
        categories = new HashMap<>();
    }

    public boolean containsKey(String category){
        return categories.containsKey(category);
    }

    public Set<String> getSubcategoryOf(String key){
        return categories.get(key);
    }

    public boolean addSubCategory(String key, String subCat){
        if (containsKey(key))
            return categories.get(key).add(subCat);
        return false;
    }

    public boolean addCategory(String key){
        if (categories.containsKey(key))
            return false;
        categories.put(key, new TreeSet<>());
        return true;
    }

    public void put(String key, Set<String> val){
        categories.put(key, val);
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
