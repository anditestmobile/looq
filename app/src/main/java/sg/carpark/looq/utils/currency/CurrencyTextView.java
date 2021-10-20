package sg.carpark.looq.utils.currency;

import android.content.Context;
import android.util.AttributeSet;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by TED on 06-Oct-20
 */
public class CurrencyTextView extends androidx.appcompat.widget.AppCompatTextView {

    String rawText;

    public CurrencyTextView(Context context) {
        super(context);

    }

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        rawText = text.toString();
        String prezzo = text.toString();
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("Rp ###,###,##0.00", symbols);
            prezzo = decimalFormat.format(new BigDecimal(prezzo));
        }catch (Exception e){}

        super.setText(prezzo, type);
    }

    @Override
    public CharSequence getText() {

        return rawText;
    }
}
