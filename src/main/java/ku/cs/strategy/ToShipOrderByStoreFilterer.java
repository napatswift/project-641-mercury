package ku.cs.strategy;

import ku.cs.models.Order;

public class ToShipOrderByStoreFilterer implements OrderFilterer{
    private String name;

    public ToShipOrderByStoreFilterer(String name) {
        this.name = name;
    }

    @Override
    public boolean mach(Order order) {
        if(!order.isShipped() && order.getStoreName().equals(name)) return true;
        return false;
    }
}
