package ku.cs.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class StoreList {
    private Collection<Store> storeList;

    public StoreList(){
        this.storeList = new TreeSet<>();
    }

    public void addStore(Store store){
        storeList.add(store);
    }

    public void removeStore(Store store){
        storeList.remove(store);
    }

    public Store findStoreByName(String nameStore){
        for(Store store : storeList) {
            if(store.getName().equals(nameStore))
                return store;
        }
        return null;
    }

    public boolean isExit(Store store){
        return storeList.contains(store);
    }

    public int size(){
        return storeList.size();
    }

    public String toCsv(){
        StringBuilder stringBuilder = new
                StringBuilder("username,name_store,stock_lower_bound");
        stringBuilder.append("\n");
        for(Store store: storeList){
            stringBuilder.append(store.toCsv());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
