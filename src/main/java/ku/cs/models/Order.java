package ku.cs.models;

public class Order {
    private String productName;
    private String productID;
    private boolean status;
    private String storeName;
    private int amount;
    private String tracking;
    private String buyerName;

    public Order(String productName, String productID, boolean status, String storeName, int amount, String tracking, String buyerName) {
        this.productName = productName;
        this.productID = productID;
        this.status = status;
        this.storeName = storeName;
        this.amount = amount;
        this.tracking = tracking;
        this.buyerName = buyerName;
    }

    public boolean isSend(){
        return status;
    }

    public void sendProduct(){
        status = true;
    }

    public String toCsv(){
        return productName + ","
                + productID + ","
                + storeName + ","
                + amount + ","
                + status + ","
                + tracking + ","
                + buyerName;
    }
}
