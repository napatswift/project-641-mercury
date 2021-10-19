package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public interface CouponType {
    double use(String code,Order order);
}
