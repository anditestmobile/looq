package sg.carpark.looq.data.model;

import androidx.annotation.Nullable;

/**
 * Created by TED on 22-Nov-20
 */
public class ParkingLot {
    private int id;
    private String image;
    private String level;
    private String name;
    private long latitude;
    private long longitude;

    public ParkingLot(String name, long latitude, long longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null)
            return false;

        ParkingLot itemCompare = (ParkingLot) obj;
        if(itemCompare.getName().equals(this.getName()))
            return true;

        return false;
    }
}
