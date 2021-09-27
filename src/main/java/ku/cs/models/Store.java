package ku.cs.models;

public class Store {
    private User owner;
    private String nameStore;
    private int stockLower;
    private ProductList products;

    public Store(User owner, String nameStore) {
        this(owner,nameStore,10);
    }

    public Store(User owner, String nameStore, int stockLower) {
        this.owner = owner;
        this.nameStore = nameStore;
        this.stockLower = stockLower;
        products = new ProductList();
    }

    public String getNameStore() {
        return nameStore;
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
       return owner.getUsername() + ","
               + "\"" + nameStore.replace("\"", "\"\"") + "\"" + ","
               + stockLower;
    }

}
