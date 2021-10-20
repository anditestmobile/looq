package sg.carpark.looq.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by TED on 29-Nov-20
 */
public class Event implements Serializable {
    private int id;
    private String[] mall;
    private String[] tenant;
    private String name;
    private String type;
    private String date_start;
    private String date_end;
    @SerializedName("ads_title")
    private String title;
    @SerializedName("ads_description")
    private String description;
    @SerializedName("ads_html")
    private String link;
    @SerializedName("ads_image")
    private String image;

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getMall() {
        return mall;
    }

    public void setMall(String[] mall) {
        this.mall = mall;
    }

    public String[] getTenant() {
        return tenant;
    }

    public void setTenant(String[] tenant) {
        this.tenant = tenant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
