package ku.cs.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductList{
    private List<Product> products;
    private Product selectedProduct;
    public enum SortType {BY_ROLLOUT_DAT, BY_LOWEST, BY_HIGHEST}
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
        for(Product product: products){
            if(product.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Product getProduct(String id){
        for(Product product: products){
            if(product.getId().equals(id)){
                return product;
            }
        }
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
        if (sortType.equals(SortType.BY_ROLLOUT_DAT))
            products.sort(Comparator.comparing(Product::getRolloutDate));
        else if (sortType.equals(SortType.BY_LOWEST))
            products.sort(Comparator.comparingDouble(Product::getPrice));
        else if (sortType.equals(SortType))
    }

    public void toTsv(String filePath){
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append("name\tproduct_id\tprice\tstore\tstock\tdescription\trating\treviews\timage");
            writer.newLine();
            for(Product product: products){
                writer.append(product.toTsv());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }
}
