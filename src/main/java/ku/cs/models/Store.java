package ku.cs.models;

public class Store {
    private final User owner;
    private final String nameStore;
    private int stockLowerBound;

    public Store(User owner, String nameStore) {
        this(owner,nameStore,10);
    }

    public Store(User owner, String nameStore, int stockLowerBound) {
        this.owner = owner;
        this.nameStore = nameStore;
        this.stockLowerBound = stockLowerBound;
    }

    public String getName() {
        return nameStore;
    }

    public User getOwner() {
        return owner;
    }

    public int getStockLowerBound() {
        return stockLowerBound;
    }

    public void setStockLowerBound(int stockLowerBound) {
        this.stockLowerBound = stockLowerBound;
    }

    public boolean stockIsLow(Product product){
        return product.getStock() <= stockLowerBound;
    }

    public String toCsv(){
       return owner.getUsername() + ","
               + "\"" + nameStore.replace("\"", "\"\"") + "\"" + ","
               + stockLowerBound;
    }

}
