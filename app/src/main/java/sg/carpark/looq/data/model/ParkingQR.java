package sg.carpark.looq.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TED on 22-Nov-20
 */
public class ParkingQR {
    @SerializedName("datas")
    private String data;
    private String name;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
