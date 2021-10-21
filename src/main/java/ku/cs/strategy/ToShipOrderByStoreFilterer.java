package ku.cs.strategy;

import ku.cs.models.Order;

public class ToShipOrderByStoreFilterer implements OrderFilterer{
    private final String name;

    public ToShipOrderByStoreFilterer(String name) {
        this.name = name;
    }

    @Override
    public boolean mach(Order order) {
        return !order.isShipped() && order.getStoreName().equals(name);
    }
}
