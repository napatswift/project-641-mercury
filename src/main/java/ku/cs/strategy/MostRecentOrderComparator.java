package ku.cs.strategy;

import ku.cs.models.Order;

import java.util.Comparator;

public class MostRecentOrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getTime().compareTo(o2.getTime()) * -1;
    }
}
