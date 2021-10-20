package sg.carpark.looq.data.database.converter;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

/**
 * Created by TED on 06-Oct-20
 */
public class BigDecimalConverter {
    @TypeConverter
    public static BigDecimal fromString(String value){
        return value != null ? new BigDecimal(value) : BigDecimal.ZERO;
    }

    @TypeConverter
    public static String bigDecimalToString(BigDecimal value){
        return value != null ? value.toString() : null;
    }
}
