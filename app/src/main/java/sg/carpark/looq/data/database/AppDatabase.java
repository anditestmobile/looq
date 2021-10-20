package sg.carpark.looq.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import sg.carpark.looq.data.database.converter.BigDecimalConverter;
import sg.carpark.looq.data.database.dao.CustomerDao;
import sg.carpark.looq.data.database.dao.MaterialDao;
import sg.carpark.looq.data.database.dao.MaterialTypeDao;
import sg.carpark.looq.data.model.Customer;
import sg.carpark.looq.data.model.Material;
import sg.carpark.looq.data.model.MaterialType;


/**
 * Created by TED on 28-Aug-20
 */

@Database(entities = {Customer.class, Material.class, MaterialType.class},
        version = 1)
@TypeConverters({BigDecimalConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "CORE";

    private static volatile AppDatabase instance;

    public static synchronized void getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
    }

    public static synchronized AppDatabase getInstance() {
        return instance;
    }

    public static void init(Context context){
        getInstance(context);
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME)
                .allowMainThreadQueries().build();
    }


    /*dao*/
    public abstract CustomerDao customerDao();
    public abstract MaterialDao materialDao();
    public abstract MaterialTypeDao materialTypeDao();
    /*public abstract UserDao userDao();
    public abstract VisitDao visitDao();
    public abstract ProductDao productDao();
    public abstract QuestionDao questionDao();
    public abstract OrderHistoryDao orderHistoryDao();
    public abstract TakingOrderDao takingOrderDao();
    public abstract TakingOrderDetailDao takingOrderDetailDao();
    public abstract TakingOrderAdditionalDao takingOrderAdditionalDao();
    public abstract CustomerShipToDao customerShipToDao();
    public abstract ProductTypeDao productTypeDao();
    public abstract DocumentTypeDao documentTypeDao();
    public abstract WarehouseDao warehouseDao();
    public abstract ProvinceDao provinceDao();*/

}
