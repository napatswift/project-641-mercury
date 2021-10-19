package ku.cs.models;

import ku.cs.strategy.MostRecentOrderComparator;
import ku.cs.strategy.OrderGetter;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class OrderList{
    private Set<Order> orders;

    public OrderList() {
        orders = new TreeSet<>();
    }

    public boolean addOrder(Order order){
        return orders.add(order);
    }

    public ArrayList<Order> getOrderList(OrderGetter orderGetter){
        return orderGetter.getOrders(orders);
    }

    public String toCsv(){
        StringBuilder result = new StringBuilder("id,product_id,amount,is_shipped,tracking_id,buyer,buy_at\n");
        for(Order order : orders)
            result.append(order.toCsv()).append("\n");
        return result.toString();
    }


}
