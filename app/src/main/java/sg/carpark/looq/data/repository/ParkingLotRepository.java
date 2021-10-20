package sg.carpark.looq.data.repository;

import android.content.Context;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.carpark.looq.data.model.ConnectionData;
import sg.carpark.looq.data.model.Mall;
import sg.carpark.looq.data.model.ParkingArea;
import sg.carpark.looq.data.model.ParkingLot;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;


/**
 * Created by TED on 07-Sep-20
 */
public class ParkingLotRepository {
    private static ParkingLotRepository instance;
    private Context context;
//    private CustomerDao customerDao;

    protected ConnectionData cd;
    protected OdooConnect oc;
    private List<HashMap<String, Object>> data = new ArrayList<>();
    private ArrayList<ParkingArea> listParking = new ArrayList<>();

    public ParkingLotRepository(Context context) {
        this.context = context;
//        customerDao = AppDatabase.getInstance().customerDao();
    }

    public static ParkingLotRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ParkingLotRepository(context);
        }

        return instance;
    }

    public List<ParkingArea> generateItems(int mallId) {
        List<ParkingArea> result = new ArrayList<>();
//        selectableItems.add(new ParkingLot("PARKING LOT A", 0, 0));
//        selectableItems.add(new ParkingLot("PARKING LOT B", 0, 0));
//        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));

        cd = (ConnectionData) Helper.getItemParam(Constants.KEY_DATA);
        oc = (OdooConnect) Helper.getItemParam(Constants.KEY_OC);

        data = new ArrayList<>();

        data = oc.search_read(
                "looq_mall.parking_area",//api
                new Object[]
                        {new Object[]//conditions/parameter
//                                {new Object[]{"mall_id", "=", mallId}}
                                {new Object[]{}}
                        }
                , Constants.GENERAL
                , Constants.FIELDS_PARKING_AREA);
        if (data != null) {
            JSONArray jsonArray = new JSONArray(data);
            Gson gson = new Gson();
            Type type = new TypeToken<List<ParkingArea>>() {
            }.getType();
            result = new ArrayList<>();
            result = gson.fromJson(String.valueOf(jsonArray), type);

        }


        return result;
    }

    public List<Mall> generateListMall(){

        return (List<Mall>) Helper.getItemParam(Constants.LIST_MALL);
    }

    public static List<ParkingLot> generateMoreItems() {
        List<ParkingLot> selectableItems = new ArrayList<>();
        selectableItems.add(new ParkingLot("PARKING LOT A", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT B", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));
        selectableItems.add(new ParkingLot("PARKING LOT C", 0, 0));

        return selectableItems;
    }

}
