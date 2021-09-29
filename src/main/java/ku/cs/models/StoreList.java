package ku.cs.models;

import java.util.ArrayList;

public class StoreList {
    private ArrayList<Store> storeList;

    public StoreList(){
        this.storeList = new ArrayList<>();
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

    public boolean isExit(String nameStore){
        for(Store store : storeList){
            if(store.getName().equals(nameStore)){
                return true;
            }
        }return false;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new
                StringBuilder("username,name_store,stock_lower");
        stringBuilder.append("\n");
        for(Store store: storeList){
            stringBuilder.append(store.toCsv());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
