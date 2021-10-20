package sg.carpark.looq.utils.location;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import sg.carpark.looq.interfaces.LocationCallback;
import sg.carpark.looq.utils.constants.Constants;


/**
 * Created by Samuel Gunawan on 12/13/2017.
 */


public class AddressResultReceiver extends ResultReceiver {
    private LocationCallback<String, Location> callbackOnResult;
    private String mAddressOutput;
    private Location mLastLocation;

    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    public void setCallback(LocationCallback<String, Location> callbackOnResult) {
        this.callbackOnResult = callbackOnResult;
    }

    public void setLastLocation(Location mLastLocation) {
        this.mLastLocation = mLastLocation;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
        // Show a toast message if an address was found.
//        if (resultCode == Constants.SUCCESS_RESULT) {
//                showToast(mAddressOutput);
//        }
        if (this.callbackOnResult != null) {
            this.callbackOnResult.onFinish(mAddressOutput, mLastLocation);
        }
    }
}