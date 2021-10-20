package sg.carpark.looq.data.model;

/**
 * Created by TED on 25-Nov-20
 */
public class Store {
    private String blockName;
    private String storeName;

    public Store() {
    }

    public Store(String blockName, String storeName) {
        this.blockName = blockName;
        this.storeName = storeName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
