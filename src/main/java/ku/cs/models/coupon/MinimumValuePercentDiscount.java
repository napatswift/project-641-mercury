package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class MinimumValuePercentDiscount extends Coupon implements CouponType {
    private final double minimumValue;
    private final double percentDiscount;

    public MinimumValuePercentDiscount(String code, Store owner, boolean status, double minimumValue, double percentDiscount) {
        super(code, owner, status);
        this.minimumValue = minimumValue;
        this.percentDiscount = percentDiscount;
    }

    @Override
    public double use(String code, Order order) {
        if(minimumValue > order.getTotal())
            return -1;

        if(super.checkCoupon(code, order) != 1)
            return super.checkCoupon(code, order);

        return order.getTotal() - (order.getTotal() * percentDiscount);
    }

    @Override
    public String toDescriptiveString() {
        return "Min. order of $" + minimumValue;
    }

    @Override
    public String toNumberOffString() {
        return (percentDiscount * 100) + "%";
    }

    @Override
    public String toCsv(){
        return super.toCsv() + ","
                + String.format("%.4f", percentDiscount) + ","
                + null + ","
                + null + ","
                + String.format("%.4f", minimumValue);
    }

    @Override
    public String toString() {
        return (percentDiscount * 100) + "% off on items with minimum order of $" + minimumValue;
    }

}
