package ku.cs.strategy;

import ku.cs.models.Order;
import java.util.ArrayList;
import java.util.Set;

public interface OrderFilterer {
    boolean mach(Order order);
}
