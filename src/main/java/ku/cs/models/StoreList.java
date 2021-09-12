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

    public boolean isExit(String nameStore){
        for(Store store : storeList){
            if(store.getName().equals(nameStore)){
                return true;
            }
        }return false;
    }

}
