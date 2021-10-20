package sg.carpark.looq.data.model;

/**
 * Created by TED on 23-Nov-20
 */
public class Featured {
    private String imgUrl;
    private String name;
    private String title;
    private String description;
    private String link;

    public Featured() {
    }

    public Featured(String imgUrl, String name, String title, String description, String link) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
