package sg.carpark.looq.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.data.model.Customer;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by TED on 31-Aug-20
 */

@Dao
public interface CustomerDao {
    @Insert(onConflict = REPLACE)
    Long[] insertAll(ArrayList<Customer> customers);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);

    @Query("SELECT * FROM customer ORDER BY name ASC")
    List<Customer> getListCustomer();

    @Query("DELETE FROM Customer")
    void deleteAll();


    @Query("SELECT * FROM customer WHERE id = :id")
    Customer getCustomerDetail(String id);

    @Query("SELECT id FROM Customer WHERE name = :customerName")
    String getIdByCustomerName(String customerName);

}
