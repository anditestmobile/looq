package sg.carpark.looq.ui.mall.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import sg.carpark.looq.data.model.StoreDirectory;
import sg.carpark.looq.data.repository.StoreDirectoryRepository;

/**
 * Created by TED on 25-Nov-20
 */
public class StoreDirectoryViewModel extends ViewModel {
    private StoreDirectoryRepository repository;
    private LiveData<List<StoreDirectory>> stores;

    public StoreDirectoryViewModel() {
        repository = StoreDirectoryRepository.getInstance();
    }

    public LiveData<List<StoreDirectory>> getStores() {
        if(stores == null){
//            stores = repository.generateLiveDataItems();
        }

        return stores;
    }
}
