package ku.cs.models;

import java.util.ArrayList;

public class OrderList {
    private ArrayList<Order> orders;

    public OrderList() {
        orders = new ArrayList<>();
    }

    public void addOrder(Order order){
        orders.add(order);
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

    public String toCsv(){
        String result = "id,product_id,amount,is_shipped,tracking_id,buyer,time\n";
        for(Order order : orders){
            result += order.toCsv() + "\n";
        }return result;
    }
}
