package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class MinimumValueDiscount extends Coupon implements CouponType {
    private final double minimumValue;
    private final double discount;

    public MinimumValueDiscount(String code, Store owner, boolean status, double minimumValue, double discount) {
        super(code, owner, status);
        this.minimumValue = minimumValue;
        this.discount = discount;
    }

    @Override
    public double use(String code, Order order) {
        if(minimumValue > order.getTotal())
            return -1;

        if(super.checkCoupon(code, order) != 1)
            return super.checkCoupon(code, order);

        return order.getTotal() - discount;
    }

    @Override
    public String toCsv(){
        return super.toCsv() + ","
                + null + ","
                + String.format("%.4f", discount) + ","
                + null + ","
                + String.format("%.4f", minimumValue);
    }

    @Override
    public String toString() {
        return this.toCsv();
    }

}
