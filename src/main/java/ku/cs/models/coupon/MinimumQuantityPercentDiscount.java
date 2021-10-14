package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class MinimumQuantityPercentDiscount implements Coupon {
    private final String code;
    private final Store owner;
    private final int minimumQuantity;
    private final double percentDiscount;

    public MinimumQuantityPercentDiscount(String code, Store owner, int minimumQuantity, double percentDiscount) {
        this.code = code;
        this.owner = owner;
        this.minimumQuantity = minimumQuantity;
        this.percentDiscount = percentDiscount;
    }

    @Override
    public double use(String code, Order order) {
        if(minimumQuantity > order.getQuantity())
            return -1;
        if(order.getProduct().getStore() != owner)
            return -1;
        if(!this.code.equals(code))
            return -1;
        return order.getTotal() - (order.getTotal() * percentDiscount);
    }

    @Override
    public String toCsv(){
        return code + ","
                + owner.getName() + ","
                + String.format("%.4f", percentDiscount) + ","
                + null + ","
                + minimumQuantity + ","
                + null;
    }

    @Override
    public boolean checkCode(String code) {
        return this.code.equals(code);
    }

    @Override
    public boolean checkStore(Store store) {
        return this.owner == store;
    }

    @Override
    public String toString() {
        return "MinimumQuantityPercentDiscount{" +
                "code='" + code + '\'' +
                ", owner=" + owner +
                ", minimumQuantity=" + minimumQuantity +
                ", percentDiscount=" + percentDiscount +
                '}';
    }
}
