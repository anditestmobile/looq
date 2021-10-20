package sg.carpark.looq.utils.helper;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;

import sg.carpark.looq.utils.constants.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by TED on 28-Jul-20
 */
public class Helper {
    private static Map<String, Object> param = new HashMap<String, Object>();

    /*Passing Data*/
    public static Object getItemParam(String key) {
        return param.get(key);
    }

    public static void setItemParam(String key, Object object) {
        param.put(key, object);
    }

    public static void removeItemParam(String key) {
        param.remove(key);
    }

    /*Strings*/
    public static String isNull(String input, String placeHolder) {

        return input != null ? input : placeHolder;
    }

    public static String generateRandomId(Date curDate) {
        return new BigInteger(String.valueOf((int) Math.floor((1 + Math.random()) * 0x1000)))
                .toString(16)
                .substring(1) + String.valueOf(curDate.getTime());
    }

    public static byte[] generateBarcodeToByte(String text) throws WriterException {
        int QR_CODE_WIDTH = 115;
        int QR_CODE_HEIGHT = 115;
        int QR_CODE_QUALITY = 100;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, QR_CODE_QUALITY, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();
        return byteArray;
    }

    public static int convertX(int oldX, int oldWidth, int newWidth){


        return (oldX * newWidth )/oldWidth;
    }


    public static SimpleDateFormat dateFormatter(String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter;
    }

    public static String isEmpty(String input, String placeHolder) {

        return input != null ? input : placeHolder;
    }

    public static String getToday(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String objectToString(Serializable obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(
                    new Base64OutputStream(baos, Base64.NO_PADDING
                            | Base64.NO_WRAP));
            oos.writeObject(obj);
            oos.close();
            return baos.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object stringToObject(String str) {
        try {
            return new ObjectInputStream(new Base64InputStream(
                    new ByteArrayInputStream(str.getBytes()), Base64.NO_PADDING
                    | Base64.NO_WRAP)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String todayDate1(String format) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(format);
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String doIfEmptyString(String input){
        String emptyPlaceHolder = "-";

        if(input != null){
            if(input.equals("false")){
                return emptyPlaceHolder;
            }
        }

        return TextUtils.isEmpty(input) ? emptyPlaceHolder : input;
    }

    public static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return day + "st";
            case 2:
                return day +"nd";
            case 3:
                return day +"rd";
            default:
                return day +"th";
        }
    }
}
