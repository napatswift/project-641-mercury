package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;
import ku.cs.models.utils.Observer;
import ku.cs.models.utils.Subject;

import java.util.ArrayList;
import java.util.List;

public class CouponList extends Subject {
    private final List<CouponType> couponTypes;
    private final Observer couponObserver;

    public CouponList() {
        this.couponTypes = new ArrayList<>();
        this.couponObserver = this::notifyObservers;
    }

    public void addCoupon(String code,Store owner,boolean status,Double minimumValue,Integer minimumQuantity,Double discount,Double percentDiscount){
        CouponType couponType;
        if(minimumQuantity == null){
            if(discount == null)
                couponType = new MinimumValuePercentDiscount(code,owner,status,minimumValue,percentDiscount);
            else
                couponType = new MinimumValueDiscount(code,owner,status,minimumValue,discount);
        }
        else {
            if(discount == null)
                couponType = new MinimumQuantityPercentDiscount(code,owner,status,minimumQuantity,percentDiscount);
            else
                couponType = new MinimumQuantityDiscount(code,owner,status,minimumQuantity, discount);
        }
        ((Coupon) couponType).addObserver(couponObserver);
        couponTypes.add(couponType);
        notifyObservers();
    }

    public double useCoupon(String code, Order order){
        for(CouponType couponType : couponTypes){
            if(couponType.use(code,order) > 0)
                return couponType.use(code,order);
        }
        return -1;
    }

    public boolean checkCouponCode(String code){
        for(CouponType couponType : couponTypes){
            if(((Coupon) couponType).checkCode(code))
                return true;
        }
        return false;
    }

    public void removeCoupon(CouponType couponType){
        for(CouponType coupon : couponTypes){
            if(coupon == couponType){
                couponTypes.remove(coupon);
                notifyObservers();
                return;
            }
        }
    }

    public ArrayList<Coupon> toListCouponInStore(Store store){
        ArrayList<Coupon> couponTypeArrayList = new ArrayList<>();
        for(CouponType couponType : couponTypes){
            if(((Coupon) couponType).checkStore(store))
                couponTypeArrayList.add((Coupon) couponType);
        }

        return couponTypeArrayList;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new StringBuilder("code,store,status,percent_discount,discount,minimum_quantity,minimum_value");
        stringBuilder.append("\n");
        for(CouponType couponType : couponTypes){
            stringBuilder.append(couponType.toCsv());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
