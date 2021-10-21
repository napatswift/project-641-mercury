package ku.cs.strategy;

import ku.cs.models.Product;

import java.util.Comparator;

public class FromMostRecentProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getRolloutDate().compareTo(o2.getRolloutDate()) * -1;
    }
}
