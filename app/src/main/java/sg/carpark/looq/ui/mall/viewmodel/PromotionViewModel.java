package sg.carpark.looq.ui.mall.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import sg.carpark.looq.data.model.Featured;
import sg.carpark.looq.data.repository.FeaturedRepository;

/**
 * Created by TED on 25-Nov-20
 */
public class PromotionViewModel extends ViewModel {
    private FeaturedRepository featuredRepository;
    private LiveData<List<Featured>> featuredList;
    private LiveData<List<Featured>> eventList;
    private Featured selectedItem;

    public PromotionViewModel() {
        featuredRepository = FeaturedRepository.getInstance();
    }

    public LiveData<List<Featured>> getFeaturedList() {
//        if(featuredList == null){
            featuredList = featuredRepository.generateLiveDataItems();
//        }

        return featuredList;
    }

    public LiveData<List<Featured>> getEventList() {
//        if(eventList == null){
            eventList = featuredRepository.generateLiveDataEvents();
//        }

        return eventList;
    }

    public Featured getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Featured selectedItem) {
        this.selectedItem = selectedItem;
    }
}
