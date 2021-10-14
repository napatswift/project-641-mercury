package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;

public class Coupon {
    private final String code;
    private final Store owner;
    private boolean status;

    public Coupon(String code, Store owner, boolean status) {
        this.code = code;
        this.owner = owner;
        this.status = status;
    }

    public boolean checkCode(String code) {
        return this.code.equals(code);
    }

    public boolean checkStore(Store store) {
        return this.owner == store;
    }

    public boolean checkStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public double checkCoupon(String code, Order order){
        if(order.getProduct().getStore() != owner)
            return -2;
        if(!this.code.equals(code))
            return -3;
        if(!status)
            return -4;
        return 1;
    }

    public String toCsv(){
        return    code   + ","
                + owner.getName()  + ","
                + status;

    }
}
