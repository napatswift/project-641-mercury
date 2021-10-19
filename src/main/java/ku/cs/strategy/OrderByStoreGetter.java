package ku.cs.strategy;

import ku.cs.models.Order;
import ku.cs.models.OrderList;

import java.util.ArrayList;
import java.util.Set;

public class OrderByStoreGetter implements OrderGetter{
    private String name;

    public OrderByStoreGetter(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<Order> getOrders(Set<Order> orders) {
        ArrayList<Order> orderList = new ArrayList<>();
        for(Order order : orders){
            if(order.getStoreName().equals(name))
                orderList.add(order);
        }
        orderList.sort(new MostRecentOrderComparator());
        return orderList;
    }
}
