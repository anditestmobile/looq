package sg.carpark.looq.data.model;

/**
 * Created by TED on 22-Nov-20
 */
public class SelectableParkingLot extends Mall {
    private boolean isSelected;

    public SelectableParkingLot(Mall mall, boolean isSelected) {
        super(mall.getName(), mall.getLatitude(), mall.getLongitude());
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
