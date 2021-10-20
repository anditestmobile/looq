package sg.carpark.looq.data.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by TED on 29-Nov-20
 */
public class Tenants implements Serializable {
    private int id;
    private String name;
    private String description;
    @JsonAdapter(BooleanAdapterFactory.class)
    @SerializedName("units_id")
    private String[] location;

    public Tenants() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }
}
