package ku.cs.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Store {
    private String username;
    private String nameStore;
    private int stockLower;
    private ProductList products;

    public Store(String nameStore) {
        this.nameStore = nameStore;
    }

    public Store(String username, String nameStore) {
        this(username,nameStore,10);

    }

    public Store(String username, String nameStore, int stockLower) {
        this.username = username;
        this.nameStore = nameStore;
        this.stockLower = stockLower;
        products = new ProductList();
    }

    public String getNameStore() {
        return nameStore;
    }
    public String getUsername() {
        return username;
    }

    public int getStockLower() {
        return stockLower;
    }

    public void setStockLower(int stockLower) {
        this.stockLower = stockLower;
    }

    public boolean stockIsLow(Product product){
        return product.getStock() <= stockLower;
    }

    public String toCsv(){
       return username + ","
               + nameStore + ","
               + stockLower;
    }

}
