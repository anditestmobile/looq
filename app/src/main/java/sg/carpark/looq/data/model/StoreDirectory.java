package sg.carpark.looq.data.model;

import java.util.List;

/**
 * Created by TED on 25-Nov-20
 */
public class StoreDirectory {
    private String floorName;

    private List<Store> storeList;

    public StoreDirectory() {
    }

    public StoreDirectory(String floorName, List<Store> storeList) {
        this.floorName = floorName;
        this.storeList = storeList;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }
}
