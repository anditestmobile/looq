package sg.carpark.looq.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import sg.carpark.looq.data.model.Material;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by TED on 31-Aug-20
 */

@Dao
public interface MaterialDao {
    @Insert(onConflict = REPLACE)
    Long[] insertAll(Material[] customers);

    @Update
    void update(Material customer);

    @Delete
    void delete(Material customer);

}
