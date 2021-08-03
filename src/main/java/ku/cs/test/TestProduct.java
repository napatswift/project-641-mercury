package ku.cs.test;

import ku.cs.models.Category;
import ku.cs.models.CsvReader;
import ku.cs.models.Product;
import ku.cs.models.SubCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

public class TestProduct {
    public static void main() {
        Product ichitan = new Product("Ichitan green tea", "image/123.jpeg", "Genmai flavoured net content: 420 ml", 20, 4);

        System.out.println(ichitan);

        Collection<Category> categories = new TreeSet<>();
        String [] lines;
        try {
            lines = CsvReader.getLines("data/sub_category.csv");
            for(String line: lines){
                String [] entry = line.split(",");
                Category newCategory = new Category(entry[1]);
                if (categories.contains(newCategory)){
                    for(Category category: categories){
                        if (category.compareTo(newCategory) == 0){
                            category.addSubCategory(new SubCategory(entry[0]));
                        }
                    }
                } else {
                    newCategory.addSubCategory(new SubCategory(entry[0]));
                    categories.add(newCategory);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Category cat: categories){
            System.out.println(cat);
        }
    }
}
