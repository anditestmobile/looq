package sg.carpark.looq.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TED on 22-Nov-20
 */
public class Parking {
    @SerializedName("area_id")
    private String[] area;
    private int id;
    @SerializedName("mall_id")
    private String[] mall;
    private String name;
    private String level;
    @SerializedName("closest_lift_lobby")
    private String closestLift;
    @SerializedName("attachment_ids_new")
    private List<ParkingQR> attachments;

    public Parking() {
    }

    public Parking(String[] area, int id, String[] mall, String name, String level, List<ParkingQR> attachments) {
        this.area = area;
        this.id = id;
        this.mall = mall;
        this.name = name;
        this.level = level;
        this.attachments = attachments;
    }

    public String[] getMall() {
        return mall;
    }

    public void setMall(String[] mall) {
        this.mall = mall;
    }

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<ParkingQR> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ParkingQR> attachments) {
        this.attachments = attachments;
    }

    public String getClosestLift() {
        return closestLift;
    }

    public void setClosestLift(String closestLift) {
        this.closestLift = closestLift;
    }
}
