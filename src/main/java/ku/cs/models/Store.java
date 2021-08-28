package ku.cs.models;

import java.util.ArrayList;

public class Store {
//    private String ID;
    private String username;
    private String nameStore;
    private ArrayList<Product> products;

    public Store(String nameStore, String username) {
        this.username = username;
        this.nameStore = nameStore;
        products = new ArrayList<>();
        //setId();
    }

    public String getName() {
        return nameStore;
    }

//    private void setId(){
//
//    }

    public void addProduct(Product product){
        products.add(product);
        product.setStore(this);
    }



}
