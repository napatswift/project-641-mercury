package ku.cs.models;

import java.util.*;

public class ProductList implements Iterable<Product>{
    private final List<Product> products;
    private Product selectedProduct;

    @Override
    public Iterator<Product> iterator() {
        // TODO implement iterator by condition if any
        return products.iterator();
    }

    public enum SortType {BY_ROLLOUT_DATE, BY_LOWEST, BY_HIGHEST}
    public SortType sortType;

    public ProductList(){
        products = new ArrayList<>();
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProductById(String id){
        products.removeIf(product -> product.getId().equals(id));
    }

    public boolean contains(String id){
        for(Product product: products)
            if(product.getId().equals(id))
                return true;
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

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void setSelectedProduct(String id) {
        this.selectedProduct = getProduct(id);
    }

    public void sort(SortType sortType) {
        if (sortType.equals(SortType.BY_ROLLOUT_DATE)) {
            products.sort(Comparator.comparing(Product::getId));
            products.sort(Comparator.comparing(Product::getRolloutDate));
        } else if (sortType.equals(SortType.BY_LOWEST)){
            products.sort(Comparator.comparingDouble(Product::getPrice));
        } else if (sortType.equals(SortType.BY_HIGHEST)){
            products.sort(Comparator.comparingDouble(Product::getPrice));
            Collections.reverse(products);
        }
    }

    public void sort(){
        sort(SortType.BY_ROLLOUT_DATE);
    }

    public void addFilter(double lowerBound, double upperBound){
        // TODO implement filtering
    }


    public String toCsv(int numCategory){
        StringBuilder stringBuilder =
                new StringBuilder("name,product_id,price,store,stock,description,rating,reviews,image,rollout_date,");
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
