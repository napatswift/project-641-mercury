package ku.cs.strategy;

import ku.cs.models.Order;

public class OrderByStoreFilterer implements OrderFilterer{
    private final String name;

    public OrderByStoreFilterer(String name) {
        this.name = name;
    }

    @Override
    public boolean mach(Order order) {
        return order.getStoreName().equals(name);
    }
}
