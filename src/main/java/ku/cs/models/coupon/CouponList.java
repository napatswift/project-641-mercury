package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

import java.util.ArrayList;
import java.util.List;

public class CouponList {
    private final List<Coupon> coupons;
    private Coupon coupon;

    public CouponList() {
        this.coupons = new ArrayList<>();
    }

    public void addCoupon(String code,Store owner,Double minimumValue,Integer minimumQuantity,Double discount,Double percentDiscount){
        if(minimumQuantity == null){
            if(discount == null)
                coupons.add(new MinimumValuePercentDiscount(code,owner,minimumValue,percentDiscount));
            else coupons.add(new MinimumValueDiscount(code,owner,minimumValue,discount));
        }
        else {
            if(discount == null)
                coupons.add(new MinimumQuantityPercentDiscount(code,owner,minimumQuantity,percentDiscount));
            else coupons.add(new MinimumQuantityDiscount(code,owner,minimumQuantity,discount));
        }
    }

    public double useCoupon(String code, Order order){
        for(Coupon coupon : coupons){
            if(coupon.use(code,order) > 0)
                return coupon.use(code,order);
        }
        return -1;
    }

    public boolean checkCouponCode(String code){
        for(Coupon coupon : coupons){
            if(coupon.checkCode(code))
                return true;
        }
        return false;
    }

    public ArrayList<Coupon> toListCouponInStore(Store store){
        ArrayList<Coupon> couponArrayList = new ArrayList<>();
        for(Coupon coupon : coupons){
            if(coupon.checkStore(store))
                couponArrayList.add(coupon);
        }
        return couponArrayList;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new StringBuilder("code,store,percent_discount,discount,minimum_quantity,minimum_value");
        stringBuilder.append("\n");
        for(Coupon coupon : coupons){
            stringBuilder.append(coupon.toCsv());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
