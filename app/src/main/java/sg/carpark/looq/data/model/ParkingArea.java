package sg.carpark.looq.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TED on 29-Nov-20
 */
public class ParkingArea implements Serializable {
    private int id;
    @SerializedName("lots_ids")
    private List<ParkingLot> lots;
    @SerializedName("mall_id")
    private String[] mall;
    private String name;

    public ParkingArea(String name) {
        this.name = name;
    }

    public ParkingArea() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ParkingLot> getLots() {
        return lots;
    }

    public void setLots(List<ParkingLot> lots) {
        this.lots = lots;
    }

    public String[] getMall() {
        return mall;
    }

    public void setMall(String[] mall) {
        this.mall = mall;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
