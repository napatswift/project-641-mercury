package ku.cs.models;

import ku.cs.models.io.CSVFile;
import ku.cs.strategy.FromMostRecentOrderComparator;
import ku.cs.strategy.OrderFilterer;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class OrderList implements CSVFile {
    private Set<Order> orders;

    public OrderList() {
        orders = new TreeSet<>();
    }

    public boolean addOrder(Order order){
        return orders.add(order);
    }

    public ArrayList<Order> getOrderList(OrderFilterer orderFilter){
        ArrayList<Order> orderList = new ArrayList<>();
        for(Order order : orders){
            if(orderFilter.mach(order))
                orderList.add(order);
        }
        orderList.sort(new FromMostRecentOrderComparator());
        return orderList;
    }

    @Override
    public String toCSV(){
        StringBuilder result = new StringBuilder("id,product_id,amount,is_shipped,tracking_id,buyer,buy_at\n");
        for(Order order : orders)
            result.append(order.toCSV()).append("\n");
        return result.toString();
    }


}
