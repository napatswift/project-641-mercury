package ku.cs.strategy;

import ku.cs.models.Order;
import ku.cs.models.OrderList;

import java.util.ArrayList;
import java.util.Set;

public interface OrderGetter {
    ArrayList<Order> getOrders(Set<Order> orders);
}
