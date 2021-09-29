package ku.cs.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private Product product;
    private final int amount;
    private boolean isShipped;
    private String tracking;
    private final User buyer;
    private LocalDateTime localDateTime;
    private String id;

    public Order(Product product, int amount, String buyerName, User buyer, LocalDateTime localDateTime) {
        this.product = product;
        this.amount = amount;
        this.buyer = buyer;
        this.localDateTime = localDateTime;
        isShipped = false;
        id = UUID.randomUUID().toString();
    }

    public Order(String id, Product product, int amount, boolean isShipped, String tracking, User buyer, LocalDateTime localDateTime) {
        this.product = product;
        this.amount = amount;
        this.isShipped = isShipped;
        this.buyer = buyer;
        this.tracking = tracking;
        this.localDateTime = localDateTime;
        this.id = id;
    }

    private void changeStatus() {
        this.isShipped = true;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
        changeStatus();
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getTime() {
        return localDateTime;
    }
    public String getStoreName(){
        return product.getStore().getName();
    }

    public boolean isShipped() {
        return isShipped;
    }

    public User getBuyer() {
        return buyer;
    }

    public int getAmount() {
        return amount;
    }

    public String getTracking() {
        return tracking;
    }



    public String toCsv(){
        return  id + ","
                + product.getId() + ","
                + amount + ","
                + isShipped + ","
                + tracking + ","
                + buyer.getUsername() + ","
                + localDateTime.toString();
    }
}
