package sg.carpark.looq.utils.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

import sg.carpark.looq.interfaces.LocationCallback;
import sg.carpark.looq.utils.constants.Constants;

/**
 * Created by TED on 11-Sep-20
 */
public class LocationUtils {

    public static final int LOCATION_PERMISSION_REQUEST = 7;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private AddressResultReceiver mResultReceiver;
    private LocationCallback<String, Location> addressCallback;
    private Activity activity;

    public LocationUtils(Activity activity) {
        this.activity = activity;
        buildLocationProvider();
    }

    //address
    public void getAddressWithPermission(LocationCallback<String, Location> callbackOnResult) {
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            mResultReceiver.setCallback(callbackOnResult);
            this.addressCallback = callbackOnResult;
            getAddress();
        }
    }

    public void buildLocationProvider() {
        mResultReceiver = new AddressResultReceiver(new Handler());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        mFusedLocationClient
                .getLastLocation()
                .addOnSuccessListener(activity, location -> {
                    if (location == null) {
                        addressCallback.onFinish(null, null);
                        return;
                    }
                    mLastLocation = location;
                    mResultReceiver.setLastLocation(mLastLocation);
                    if (!Geocoder.isPresent()) {
                        if (addressCallback != null) {
                            addressCallback.onFinish("Geocoder not available", location);
                        }
                        return;
                    }
                    startIntentService();
                })
                .addOnFailureListener(activity, e -> {
                    addressCallback.onFinish(null, null);
                });
    }

    private void startIntentService() {
        Intent intent = new Intent(activity, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        activity.startService(intent);
    }


    public boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }


    public void requestPermissions() {
        startLocationPermissionRequest();
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST);
    }

    public static String getCurrentAddress(Context context, Double lat, Double lng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            //            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
            /*String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();*/
            return addresses.get(0).getAddressLine(0); //+ "\n" + city + "\n" + state;
        } catch (Exception e) {
            return null;
        }
    }

}
