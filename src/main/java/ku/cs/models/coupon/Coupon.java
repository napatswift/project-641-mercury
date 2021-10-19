package ku.cs.models.coupon;

import ku.cs.models.Order;
import ku.cs.models.Store;
import ku.cs.models.io.CSVFile;
import ku.cs.observer.Subject;

abstract public class Coupon extends Subject implements CSVFile {
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

    public boolean isActive(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
        notifyObservers();
    }
    public String getCode() { return code; }

    public Store getOwner() { return owner; }

    public double checkCoupon(String code, Order order){
        if(order.getProduct().getStore() != owner)
            return -2;
        if(!this.code.equals(code))
            return -3;
        if(!status)
            return -4;
        return 1;
    }

    abstract public String toDescriptiveString();
    abstract public String toNumberOffString();

    @Override
    public String toCSV(){
        return  code   + ","
                + owner.getName()  + ","
                + status;
    }
}
