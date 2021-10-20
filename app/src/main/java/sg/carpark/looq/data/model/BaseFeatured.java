package sg.carpark.looq.data.model;

import java.util.List;

/**
 * Created by TED on 25-Nov-20
 */
public class BaseFeatured {
    private String title;
    private List<Featured> featuredList;

    private List<StoreDirectory> storeDirectoryList;

    public BaseFeatured() {
    }

    public BaseFeatured(String title, List<Featured> featuredList) {
        this.title = title;
        this.featuredList = featuredList;
    }

    public BaseFeatured(String title, List<Featured> featuredList, List<StoreDirectory> storeDirectoryList) {
        this.title = title;
        this.featuredList = featuredList;
        this.storeDirectoryList = storeDirectoryList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Featured> getFeaturedList() {
        return featuredList;
    }

    public void setFeaturedList(List<Featured> featuredList) {
        this.featuredList = featuredList;
    }

    public List<StoreDirectory> getStoreDirectoryList() {
        return storeDirectoryList;
    }

    public void setStoreDirectoryList(List<StoreDirectory> storeDirectoryList) {
        this.storeDirectoryList = storeDirectoryList;
    }
}
