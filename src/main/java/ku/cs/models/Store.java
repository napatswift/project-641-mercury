package ku.cs.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Store {
    private String username;
    private String nameStore;
    private ProductList products;

    public Store(String nameStore) {
        this.nameStore = nameStore;
    }

    public Store(String nameStore, String username) {
        this.username = username;
        this.nameStore = nameStore;
        products = new ProductList();
    }

    public String getName() {
        return nameStore;
    }


    public void toCsv() throws IOException {
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter("data/store.csv",true);
            PrintWriter out = new PrintWriter(new BufferedWriter(fileWriter));
            out.println(username + "," + nameStore);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
