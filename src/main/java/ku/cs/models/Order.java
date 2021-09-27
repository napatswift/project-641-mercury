package ku.cs.models;

import java.time.LocalDateTime;

public class Order {
    private final String productName;
    private final String productId;
    private final String storeName;
    private final int amount;
    private String status = "to sent";
    private String tracking = "-";
    private final String buyerName;
    private LocalDateTime localDateTime;

    public Order(String productName, String productId, String storeName, int amount, String buyerName, LocalDateTime localDateTime) {
        this.productName = productName;
        this.productId = productId;
        this.storeName = storeName;
        this.amount = amount;
        this.buyerName = buyerName;
        this.localDateTime = localDateTime;
    }

    public Order(String productName, String productId, String storeName, int amount, String status, String tracking, String buyerName, LocalDateTime localDateTime) {
        this.productName = productName;
        this.productId = productId;
        this.storeName = storeName;
        this.amount = amount;
        this.status = status;
        this.tracking = tracking;
        this.buyerName = buyerName;
        this.localDateTime = localDateTime;
    }

    private void changeStatus() {
        this.status = "sent";;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
        changeStatus();
    }

    public String getProductName() {
        return productName;
    }

    public String getProductId() {
        return productId;
    }

    public String getStatus() {
        return status;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getAmount() {
        return amount;
    }

    public String getTracking() {
        return tracking;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String toCsv(){
        return productName + ","
                + productId + ","
                + storeName + ","
                + amount + ","
                + status + ","
                + tracking + ","
                + buyerName;
    }
}
