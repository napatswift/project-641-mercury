package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class MinimumQuantityDiscount extends Coupon implements CouponType {
    private final int minimumQuantity;
    private final double discount;

    public MinimumQuantityDiscount(String code, Store owner, boolean status, int minimumQuantity, double discount) {
        super(code, owner, status);
        this.minimumQuantity = minimumQuantity;
        this.discount = discount;
    }

    public double use(String code, Order order) {
        if(minimumQuantity > order.getQuantity())
            return -1;

        if(super.checkCoupon(code, order) != 1)
            return super.checkCoupon(code, order);

        return order.getTotal() - discount;
    }

    @Override
    public String toCsv(){
        return super.toCsv() + ","
                + null + ","
                + discount + ","
                + minimumQuantity + ","
                + null;
    }

    @Override
    public String toDescriptiveString() {
        return "Min. buy " + minimumQuantity + " units";
    }

    @Override
    public String toNumberOffString() {
        return "$" + discount;
    }

    @Override
    public String toString() {
        return "Buy " + minimumQuantity + " units\nget $" + discount +" off";
    }
}
