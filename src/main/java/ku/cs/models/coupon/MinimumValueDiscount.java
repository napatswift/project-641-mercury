package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class MinimumValueDiscount implements Coupon {
    private final String code;
    private final Store owner;
    private final double minimumValue;
    private final double discount;

    public MinimumValueDiscount(String code, Store owner, double minimumValue, double discount) {
        this.code = code;
        this.owner = owner;
        this.minimumValue = minimumValue;
        this.discount = discount;
    }

    @Override
    public double use(String code, Order order) {
        if(minimumValue > order.getTotal())
            return -1;
        if(order.getProduct().getStore() != owner)
            return -1;
        if(!this.code.equals(code))
            return -1;
        return order.getTotal() - discount;
    }

    @Override
    public String toCsv(){
        return code + ","
                + owner.getName() + ","
                + null + ","
                + String.format("%.4f", discount) + ","
                + null + ","
                + String.format("%.4f", minimumValue);
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
        return "MinimumValueDiscount{" +
                "code='" + code + '\'' +
                ", owner=" + owner +
                ", minimumValue=" + minimumValue +
                ", discount=" + discount +
                '}';
    }
}
