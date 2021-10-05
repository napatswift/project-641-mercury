package ku.cs.models;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class OrderList {
    private Set<Order> orders;

    public OrderList() {
        orders = new TreeSet<>();
    }

    public boolean addOrder(Order order){
        return orders.add(order);
    }

    public ArrayList<Order> getOrdersByStore(String name){
        ArrayList<Order> orderReturn = new ArrayList<>();
        for(Order order : orders){
            if(order.getStoreName().equals(name)){
                orderReturn.add(order);
            }
        }
        return orderReturn;
    }

    public static ArrayList<Order> getToShipOrder(ArrayList<Order> orders){
        ArrayList<Order> orderArrayList = new ArrayList<>();
        for(Order order : orders){
            if(!order.isShipped()){
                orderArrayList.add(order);
            }
        }return orderArrayList;
    }

    public static ArrayList<Order> getShipedOrder(ArrayList<Order> orders){
        ArrayList<Order> orderArrayList = new ArrayList<>();
        for(Order order : orders){
            if(order.isShipped()){
                orderArrayList.add(order);
            }
        }return orderArrayList;
    }

    public String toCsv(){
        StringBuilder result = new StringBuilder("id,product_id,amount,is_shipped,tracking_id,buyer,buy_at\n");
        for(Order order : orders)
            result.append(order.toCsv()).append("\n");
        return result.toString();
    }
}
