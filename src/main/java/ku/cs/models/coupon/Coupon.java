package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public interface Coupon {
    double use(String code,Order order);
    String toCsv();
    boolean checkCode(String code);
    boolean checkStore(Store store);
}
