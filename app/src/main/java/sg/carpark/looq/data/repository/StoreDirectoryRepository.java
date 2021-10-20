package sg.carpark.looq.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.carpark.looq.data.model.ConnectionData;
import sg.carpark.looq.data.model.Event;
import sg.carpark.looq.data.model.Featured;
import sg.carpark.looq.data.model.Mall;
import sg.carpark.looq.data.model.Store;
import sg.carpark.looq.data.model.StoreDirectory;
import sg.carpark.looq.data.model.Tenants;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;


/**
 * Created by TED on 07-Sep-20
 */
public class StoreDirectoryRepository {
    private static StoreDirectoryRepository instance;
    private Context context;

    protected ConnectionData cd;
    protected OdooConnect oc;
    private List<HashMap<String, Object>> data = new ArrayList<>();

    public StoreDirectoryRepository() {
//        customerDao = AppDatabase.getInstance().customerDao();
    }

    public static StoreDirectoryRepository getInstance() {
        if (instance == null) {
            instance = new StoreDirectoryRepository();
        }

        return instance;
    }

    public static List<Featured> generateItems() {
        List<Featured> selectableItems = new ArrayList<>();

//        String imgUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTyveXtoGp6UVDlC8CAsywNTkayKYVecUBRlQ&usqp=CAU";
//        String dummyTitle = "Phase 2 Re-Opening at Jurong";
//        String dummyDescription = "As the nation gears up for post circuit breaker Phase 2 re-opening";
//
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));
//        selectableItems.add(new Featured(imgUrl, dummyTitle, dummyDescription));

        return selectableItems;
    }

    public List<Store> generateListStore(int mallId){
        List<Store> storeList = new ArrayList<>();

//        List<Mall> listMall = (List<Mall>) Helper.getItemParam(Constants.LIST_MALL);
        List<Mall> listMall = generateMall(mallId);
        Mall selectedMall = new Mall();
        if(listMall != null && !listMall.isEmpty()){
            selectedMall = listMall.get(0);
        }

        if(selectedMall.getTenants() != null && !selectedMall.getTenants().isEmpty()){
            for(Tenants tenant : selectedMall.getTenants()){
                storeList.add(new Store(tenant.getLocation()[1], tenant.getName()));//todo pisah lantainya
            }


        }


        return storeList;
    }
    public static List<Store> generateSecondListStore(){
        List<Store> storeList = new ArrayList<>();

        storeList.add(new Store("#02-01", "M&S"));
        storeList.add(new Store("#02-02", "Sketchers"));
        storeList.add(new Store("#02-03", "Mango"));

        return storeList;
    }
    public static List<Store> generateThirdListStore(){
        List<Store> storeList = new ArrayList<>();

        storeList.add(new Store("#03-01", "Bershka"));
        storeList.add(new Store("#03-02", "Stradivarius"));
        storeList.add(new Store("#03-03", "Pull&Bear"));
        storeList.add(new Store("#03-04", "Uniqlo"));
        storeList.add(new Store("#03-05", "Forever 21"));

        return storeList;
    }

    public LiveData<List<StoreDirectory>> generateLiveDataItems(int mallId) {
        List<StoreDirectory> selectableItems = new ArrayList<>();

        selectableItems.add(new StoreDirectory("Grd Floor", generateListStore(mallId)));
//        selectableItems.add(new StoreDirectory("2nd Floor", generateSecondListStore()));
//        selectableItems.add(new StoreDirectory("3rd Floor", generateThirdListStore()));


        MutableLiveData<List<StoreDirectory>> liveDataItems = new MutableLiveData<>();
        liveDataItems.setValue(selectableItems);

        return liveDataItems;
    }
    public List<StoreDirectory> generateListTenant(int mallId) {
        List<StoreDirectory> selectableItems = new ArrayList<>();
//        List<Mall> listMall = (List<Mall>) Helper.getItemParam(Constants.LIST_MALL);
        selectableItems.add(new StoreDirectory("01 Floor", generateListStore(mallId)));
//        selectableItems.add(new StoreDirectory("2nd Floor", generateSecondListStore()));
//        selectableItems.add(new StoreDirectory("3rd Floor", generateThirdListStore()));



        return selectableItems;
    }


    //new
    public List<Mall> generateMall(int mallId){

        List<Mall> result = new ArrayList<>();

        cd = (ConnectionData) Helper.getItemParam(Constants.KEY_DATA);
        oc = (OdooConnect) Helper.getItemParam(Constants.KEY_OC);

        data = new ArrayList<>();

        data = new ArrayList<>();

        data = oc.search_read(
                "looq_mall.mall",//api
                new Object[]
                        {new Object[]//conditions/parameter
                                {new Object[]{"id", "=", mallId}}
                        }
                , Constants.GENERAL
                , Constants.FIELDS_MALL);
        if (data != null) {
            JSONArray jsonArray = new JSONArray(data);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Mall>>() {
            }.getType();
            result = new ArrayList<>();
            result = gson.fromJson(String.valueOf(jsonArray), type);

        }

        return result;
    }
}
