package ku.cs.models;

import ku.cs.models.io.CSVFile;

import java.util.Collection;
import java.util.TreeSet;

public class StoreList implements CSVFile {
    private final Collection<Store> storeList;

    public StoreList(){
        this.storeList = new TreeSet<>();
    }

    public void addStore(Store store){ storeList.add(store); }

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

    @Override
    public String toCSV(){
        StringBuilder stringBuilder = new
                StringBuilder("username,name_store,stock_lower_bound");
        stringBuilder.append("\n");
        for(Store store: storeList){
            stringBuilder.append(store.toCSV());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
