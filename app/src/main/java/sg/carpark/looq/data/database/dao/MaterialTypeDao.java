package sg.carpark.looq.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import sg.carpark.looq.data.model.MaterialType;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by TED on 31-Aug-20
 */

@Dao
public interface MaterialTypeDao {
    @Insert(onConflict = REPLACE)
    Long[] insertAll(MaterialType[] customers);

    @Update
    void update(MaterialType customer);

    @Delete
    void delete(MaterialType customer);

}
