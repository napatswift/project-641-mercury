package ku.cs.models;

import java.util.Collection;

public class Store {
    private String name;
    private User owner;
    private Collection<Product> products;

    public Store(String name, User user){
        this.name = name;
        this.owner = user;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public Collection<Product> getProducts() {
        return products;
    }
}
