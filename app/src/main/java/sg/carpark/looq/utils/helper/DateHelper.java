package sg.carpark.looq.utils.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TED on 28-Jul-20
 */
public class DateHelper {
    /*Date*/
    public static String getDateStringFromLong(Long dateLong, String pattern) {
        Date date = new Date(dateLong);
        SimpleDateFormat df2 = new SimpleDateFormat(pattern);

        return df2.format(date);
    }

    public static SimpleDateFormat dateFormatter(String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter;
    }

    public static Date today() {
        final Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }


}
