package ku.cs.strategy;

import ku.cs.models.Product;

import java.util.Comparator;

public class FromHighestPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return Double.compare(o1.getPrice(), o2.getPrice()) * -1;
    }
}
