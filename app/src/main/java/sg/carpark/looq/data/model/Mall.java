package sg.carpark.looq.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TED on 29-Nov-20
 */
public class Mall implements Serializable {
    private int id;
    private String name;
    @SerializedName("tenant_ids")
    private List<Tenants> tenants;
    @JsonAdapter(BooleanAdapterFactory.class)
    @SerializedName(("parking_areas_ids"))
    private String[] parking_areas;
    private String latitude;
    private String longitude;
    private String location;


    public Mall() {
    }

    public Mall(String name, String latitude, String longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public List<Tenants> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenants> tenants) {
        this.tenants = tenants;
    }

    public String[] getParking_areas() {
        return parking_areas;
    }

    public void setParking_areas(String[] parking_areas) {
        this.parking_areas = parking_areas;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null)
            return false;

        Mall itemCompare = (Mall) obj;
        if(itemCompare.getName().equals(this.getName()))
            return true;

        return false;
    }
}
