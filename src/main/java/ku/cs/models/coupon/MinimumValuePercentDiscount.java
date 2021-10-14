package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class MinimumValuePercentDiscount implements Coupon {
    private final String code;
    private final Store owner;
    private final double minimumValue;
    private final double percentDiscount;

    public MinimumValuePercentDiscount(String code, Store owner, double minimumValue, double percentDiscount) {
        this.code = code;
        this.owner = owner;
        this.minimumValue = minimumValue;
        this.percentDiscount = percentDiscount;
    }

    @Override
    public double use(String code, Order order) {
        if(minimumValue > order.getTotal())
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
        return "MinimumValuePercentDiscount{" +
                "code='" + code + '\'' +
                ", owner=" + owner +
                ", minimumValue=" + minimumValue +
                ", percentDiscount=" + percentDiscount +
                '}';
    }
}
