package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class MinimumQuantityPercentDiscount extends Coupon implements CouponType {
    private final int minimumQuantity;
    private final double percentDiscount;

    public MinimumQuantityPercentDiscount(String code, Store owner, boolean status, int minimumQuantity, double percentDiscount) {
        super(code, owner, status);
        this.minimumQuantity = minimumQuantity;
        this.percentDiscount = percentDiscount;
    }

    @Override
    public double use(String code, Order order) {
        if(super.checkCoupon(code, order) < 0)
            return super.checkCoupon(code, order);

        if(minimumQuantity > order.getQuantity())
            return -2;

        return order.getTotal() - (order.getTotal() * percentDiscount);
    }

    @Override
    public String toDescriptiveString() {
        return "Min. buy " + minimumQuantity + " units";
    }

    @Override
    public String toNumberOffString() {
        return (percentDiscount * 100) + "%";
    }

    @Override
    public String toCSV(){
        return super.toCSV() + ","
                + String.format("%.4f", percentDiscount) + ","
                + null + ","
                + minimumQuantity + ","
                + null;
    }

    @Override
    public String toString() {
        return "Buy " + minimumQuantity + " units get " + (percentDiscount * 100) +"% off";
    }

}
