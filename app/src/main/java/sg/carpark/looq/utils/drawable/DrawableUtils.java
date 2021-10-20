package sg.carpark.looq.utils.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarUtils;
import sg.carpark.looq.R;


/**
 * Created by TED on 04-Oct-20
 */
public class DrawableUtils {


    public static Drawable getCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.white, 10);

        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }


    private DrawableUtils() {
    }

}
