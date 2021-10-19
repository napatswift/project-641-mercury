package ku.cs.strategy;

import ku.cs.models.Order;

public class OrderByStoreFilterer implements OrderFilterer{
    private String name;

    public OrderByStoreFilterer(String name) {
        this.name = name;
    }

    @Override
    public boolean mach(Order order) {
        if(order.getStoreName().equals(name))
            return true;
        return false;
    }
}
