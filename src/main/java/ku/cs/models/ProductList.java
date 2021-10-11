package ku.cs.models;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import ku.cs.strategy.FromHighestPriceComparator;
import ku.cs.strategy.FromLowestPriceComparator;
import ku.cs.strategy.MostRecentProductComparator;

import java.util.*;

public class ProductList implements Iterable<Product> {
    private final ArrayList<Product> products;
    private final Set<String> idSet;
    private Product selectedProduct;

    public enum SortType {BY_ROLLOUT_DATE, BY_LOWEST, BY_HIGHEST}

    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }

    public Iterator<Product> iterator(double lowerBound, double upperBound, String category){
        return products.stream()
                .filter(p -> p.getPrice() >= lowerBound
                        && p.getPrice() <= upperBound
                        && p.getStock() > 0
                        && (category == null || p.containsCategory(category)))
                .iterator();

    }

    public ProductList(){
        products = new ArrayList<>();
        idSet = new TreeSet<>();
    }

    public boolean containsId(String id){
        return idSet.contains(id);
    }

    public boolean addProduct(Product product){
        if (!idSet.contains(product.getId())) {
            products.add(product);
            idSet.add(product.getId());
            return true;
        }
        return false;
    }

    public Product getProduct(String id){
        for(Product product: products)
            if(product.getId().equals(id))
                return product;
        return null;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public ArrayList<Product> getProductByNameStore(String name){
        ArrayList<Product> productArrayList = new ArrayList<>();
        for(Product product : products){
            if(product.getStore().getName().equals(name)){
                productArrayList.add(product);
            }
        }return productArrayList;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void sort(SortType sortType) {
        if (sortType.equals(SortType.BY_ROLLOUT_DATE)) {
            products.sort(new MostRecentProductComparator());
        } else if (sortType.equals(SortType.BY_LOWEST)){
            products.sort(new FromLowestPriceComparator());
        } else if (sortType.equals(SortType.BY_HIGHEST)){
            products.sort(new FromHighestPriceComparator());
        }
    }

    private int calcMaxSubCategory(){
        int max = 0;
        for (Product product: products){
            int curr = 0;
            for (Category category: product.getCategories()){
                curr += category.getSubCategories().size();
            }
            if (max < curr) max = curr;
        }
        return max;
    }

    public void sort(){
        sort(SortType.BY_ROLLOUT_DATE);
    }

    public int size(){
        return products.size();
    }

    public String toCsv(){
        int numCategory = calcMaxSubCategory();
        StringBuilder stringBuilder =
                new StringBuilder(
                        "name,product_id,price,store,stock,description,rating,reviews,image,rollout_date,");
        StringJoiner stringJoiner = new StringJoiner(",");

        for (int i = 0; i < numCategory; i++) {
            stringJoiner.add("category_" + i);
        }

        stringBuilder.append(stringJoiner);
        stringBuilder.append("\n");
        for(Product product: products) {
            stringBuilder.append(product.toCsv(numCategory));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
