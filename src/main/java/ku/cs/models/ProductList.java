package ku.cs.models;

import java.util.*;

public class ProductList implements Iterable<Product>{
    private final List<Product> products;
    private Product selectedProduct;
    private Collection<String> categories = new TreeSet<>();

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

    public void sort(SortType sortType){
        // TODO fix bug
        if (sortType.equals(SortType.BY_ROLLOUT_DATE))
            products.sort(Comparator.comparing(Product::getRolloutDate));
        else if (sortType.equals(SortType.BY_LOWEST))
            products.sort(Comparator.comparingDouble(Product::getPrice));
        else if (sortType.equals(SortType.BY_HIGHEST)){
            products.sort(Comparator.comparingDouble(Product::getPrice));
            Collections.reverse(products);
        }
    }

    public void addCategory(String category){
        categories.add(category);
    }

    public Collection<String> getCategories() {
        return categories;
    }

    public void sort(){
        sort(SortType.BY_ROLLOUT_DATE);
    }

    public void addFilter(double lowerBound, double upperBound){
        // TODO implement filtering
    }

    public String toTsv(){
            StringBuilder stringBuilder = new StringBuilder("name\tproduct_id\tprice\tstore\tstock\tdescription\trating\treviews\timage\trollout_date");
            StringJoiner stringJoiner = new StringJoiner("\t");

            for (int i = 0; i < categories.size(); i++) {
                stringJoiner.add("category_" + i);
            }
            stringBuilder.append(stringJoiner.toString());
            stringBuilder.append("\n");
            for(Product product: products) {
                stringBuilder.append(product.toTsv());
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
    }
}