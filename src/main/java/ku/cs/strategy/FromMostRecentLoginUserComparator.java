package ku.cs.strategy;

import ku.cs.models.User;

import java.util.Comparator;

public class FromMostRecentLoginUserComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getLoginDateTime().compareTo(o2.getLoginDateTime()) * -1;
    }
}
