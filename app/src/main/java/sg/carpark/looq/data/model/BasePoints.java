package sg.carpark.looq.data.model;

/**
 * Created by TED on 01-Dec-20
 */
public class BasePoints {
    private String typeName;

    public BasePoints() {
    }

    public BasePoints(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
