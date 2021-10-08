package ku.cs.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order implements Comparable<Order> {
    private final Product product;
    private final int quantity;
    private boolean isShipped;
    private String tracking;
    private final User buyer;
    private final LocalDateTime localDateTime;
    private final String id;

    public Order(Product product, int quantity, User buyer) {
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
        this.localDateTime = LocalDateTime.now();
        isShipped = false;
        id = UUID.randomUUID().toString();
    }

    public Order(String id, Product product, int quantity, boolean isShipped, String tracking, User buyer, LocalDateTime localDateTime) {
        this.product = product;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public String getTracking() {
        return tracking;
    }



    public String toCsv(){
        return  id + ","
                + product.getId() + ","
                + quantity + ","
                + isShipped + ","
                + tracking + ","
                + buyer.getUsername() + ","
                + localDateTime.toString();
    }

    @Override
    public int compareTo(Order o) {
        return this.id.compareTo(o.id);
    }
}
