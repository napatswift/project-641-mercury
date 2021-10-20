package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;
import ku.cs.models.io.CSVFile;
import ku.cs.observer.Observer;
import ku.cs.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class CouponList extends Subject implements CSVFile {
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
            if(((Coupon) couponType).checkCode(code))
                return couponType.use(code,order);
        }
        return -5;
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

    @Override
    public String toCSV(){
        StringBuilder stringBuilder
                = new StringBuilder("code,store,status,percent_discount,discount,minimum_quantity,minimum_value");
        stringBuilder.append("\n");
        for(CouponType couponType : couponTypes){
            stringBuilder.append(((Coupon) couponType).toCSV());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
