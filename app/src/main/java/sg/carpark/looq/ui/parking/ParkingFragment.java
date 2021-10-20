package sg.carpark.looq.ui.parking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sg.carpark.looq.data.model.Event;
import sg.carpark.looq.data.model.Mall;
import sg.carpark.looq.data.model.Parking;
import sg.carpark.looq.data.model.ParkingArea;
import sg.carpark.looq.data.model.ParkingQR;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.BackgroundHelper;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;
import sg.carpark.looq.utils.interfaces.CallbackOnResult;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sg.carpark.looq.R;
import sg.carpark.looq.data.model.ParkingLot;
import sg.carpark.looq.data.model.SelectableParkingLot;
import sg.carpark.looq.data.repository.ParkingLotRepository;
import sg.carpark.looq.databinding.DialogCheckInSuccessBinding;
import sg.carpark.looq.databinding.FragmentHomeBinding;
import sg.carpark.looq.ui.base.BaseFragment;
import sg.carpark.looq.ui.main.MainActivity;
import sg.carpark.looq.utils.location.GPSTracker;
import sg.carpark.looq.utils.session.SessionManager;

import org.json.JSONArray;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by TED on 18-Nov-20
 */
public class ParkingFragment extends BaseFragment implements OnMapReadyCallback {
    private FragmentHomeBinding binding;

    private MapView mapView;
    private GoogleMap map;
    private CameraPosition cameraPosition;
    private GPSTracker gpsTracker;
    private LatLng currentLocation;

    private SelectableParkingLotAdapter adapter;
    private boolean error = true;

    ImageView drawingImageView;
    Handler mHandlerAnimation = null;
    Runnable mRunnableAnimation = null;
    Canvas canvas;
    int startx = 0, starty = 0, endx = 0, endy = 0;
    Paint paint;

    private List<HashMap<String, Object>> data = new ArrayList<>();
    private HashMap<String, Object> dataResult;
    protected int PARAM = 0;
    protected String errorMessage;
    private ArrayList<Mall> listMall = new ArrayList<>();
    private ArrayList<ParkingArea> listParkingArea = new ArrayList<>();
    private ArrayList<Event> listAllEvent = new ArrayList<>();
    private ArrayList<Event> listPromotion = new ArrayList<>();
    private ArrayList<Event> listEvent = new ArrayList<>();
    private ArrayList<Parking> listParking = new ArrayList<>();

    //    private List<ParkingArea> selectableItems = new ArrayList<>();
    private List<Mall> selectableItems = new ArrayList<>();

    List<String> lotNames = new ArrayList<>();
    List<String> lotLevels = new ArrayList<>();
    List<String> lotStartingPoints = new ArrayList<>();

    double latitude;
    double longitude;

    private String[] qrCode;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*transition sample*/
        setEnterTransition(createXSharedTransition(true));
        setReenterTransition(createXSharedTransition(false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initMap(savedInstanceState);


        session = new SessionManager(requireContext());


//        drawThatzzLine();
        init();

        if(session.isMallIdExist()){
            showCheckOutDetail(true);
            funcLayoutCheckOut();
        }else{
            if(Helper.getItemParam(Constants.ALREADY_LOADED) == null){
                PARAM = 0;
                new ConnectionOdoo().execute();
                progress.show();
            }
            funcLayoutCheckIn();
        }
        return binding.getRoot();
    }

    public void moveCamera(SelectableParkingLot item){
        try {
//            item.getLatitude().split("°");
//            String latitude = item.getLatitude().replaceAll("[^A-Za-z0-9]", " ");
//            String longitude = item.getLongitude().replaceAll("[^A-Za-z0-9]", " ");
            String latitude = item.getLatitude();
            String longitude = item.getLongitude();
            LatLng location = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));  //move camera to location
            if (map != null) {
                Marker marker = map.addMarker(new MarkerOptions().position(location));
            }
        }catch (Exception e){

        }

    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        checkLocationPermission();
    }

    @Override
    public void onDestroyView() {
        binding.mapView.onDestroy();
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {

            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(requireContext());

            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.setBuildingsEnabled(false);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentLocation).zoom(16.0f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(cameraUpdate);

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(currentLocation);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title("PARKING LOT A");

            // Clears the previously touched position
            googleMap.clear();

            // Animating to the touched position
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));

            // Placing a marker on the touched position
//            googleMap.addMarker(markerOptions);

            int strokeColor = 0xffd15fee; //red outline
            int shadeColor = 0x44d15fee; //opaque red fill
            googleMap.addCircle(new CircleOptions()
                    .center(currentLocation)
                    .radius(100)
                    .strokeColor(strokeColor)
                    .fillColor(shadeColor));
            map = googleMap;
        } else {
            map = null;
        }
    }

    @Override
    public void onResume() {
        try {
            binding.mapView.onResume();
        } catch (NullPointerException ignored) {

        }
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            binding.mapView.onPause();
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            binding.mapView.onLowMemory();
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(requireContext(), result.getContents(), Toast.LENGTH_SHORT).show();
                qrCode = result.getContents().split("\n");
                PARAM = 3;
                new ConnectionOdoo().execute();
                progress.show();
//                doShowDialogCheckInSuccess();
//                showCheckOutDetail(true);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean getMyLocation() {
        gpsTracker = new GPSTracker(requireContext());
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            currentLocation = new LatLng(latitude, longitude);
            return true;
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
            return false;
        }
    }

    private void checkLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};

        Permissions.check(getContext(), permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                setMap();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                checkLocationPermission();
            }
        });
    }

    private void initMap(Bundle savedInstanceState) {
        binding.mapView.onCreate(savedInstanceState);
    }

    private void funcLayoutCheckOut() {
        String parkingJson = session.getAttachments().get(Constants.KEY_ATTACHMENT);
        List<Parking> parking = new ArrayList<>();
        Gson gson = new Gson();

        Type type = new TypeToken<List<Parking>>() {
        }.getType();
        parking = gson.fromJson(String.valueOf(parkingJson), type);

        binding.btnIndoorMap.setVisibility(View.GONE);

        lotNames = new ArrayList<>();
        lotLevels = new ArrayList<>();
        //dummy

        lotStartingPoints = new ArrayList<>();
        List<ParkingQR> attachmentList = new ArrayList<>();

        for(Parking temp : parking) {
            lotStartingPoints.add(temp.getClosestLift());
            attachmentList.add(temp.getAttachments().get(0));
        }

        ArrayAdapter<String> lspAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lotStartingPoints);
        binding.layoutCheckOutDetail.spinnerStartingPoint.setAdapter(lspAdapter);


//        binding.layoutCheckOutDetail.spinnerStartingPoint.setText(parking.getClosestLift(),false);
//        binding.layoutCheckOutDetail.spinnerStartingPoint.setFreezesText(false);
//
        binding.layoutCheckOutDetail.spinnerStartingPoint.setText(lotStartingPoints.get(0), false);



        String imageBytes= null;
        if(attachmentList != null) {
            imageBytes = attachmentList.get(0).getData();
//            for (ParkingQR parkingQR : attachmentList) {
////                if (parkingQR.getName().contains("a.png".toLowerCase())) {
//                    imageBytes = parkingQR.getData();
//                    break;
////                }
//            }
        }
        byte[] imageByteArray = null;
        if(imageBytes != null){
            imageByteArray = Base64.decode(imageBytes, Base64.DEFAULT);
        }
        Glide.with(requireContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .error(R.drawable.ic_no_image))
                .load(imageByteArray)
                .transition(withCrossFade())
                .into(binding.ivIndoorMap);

        binding.layoutCheckOutDetail.spinnerStartingPoint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.layoutCheckOutDetail.spinnerStartingPoint.showDropDown();
                return false;
            }
        });
        List<ParkingQR> finalAttachmentList = attachmentList;
        binding.layoutCheckOutDetail.spinnerStartingPoint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO ganti peta dan tulisannya

                String imageBytes= null;
                imageBytes = finalAttachmentList.get(position).getData();
//                if(position == 0){
//                    for(ParkingQR parkingQR : finalAttachmentList){
//                        if(parkingQR.getName().contains("a.png".toLowerCase())){
//                            imageBytes = parkingQR.getData();
//                            break;
//                        }
//                    }
//                }else{
//                    for(ParkingQR parkingQR : finalAttachmentList){
//                        if(parkingQR.getName().contains("b.png".toLowerCase())){
//                            imageBytes = parkingQR.getData();
//                            break;
//                        }
//                    }
//                }

                if(imageBytes != null){
                    byte[] imageByteArray = Base64.decode(imageBytes, Base64.DEFAULT);
                    Glide.with(requireContext())
                            .load(imageByteArray)
                            .transition(withCrossFade())
                            .into(binding.ivIndoorMap);
                }
            }
        });

        //TODO benerin
        /*if (selectableItems != null && !selectableItems.isEmpty()) {
            for (ParkingLot lot : selectableItems.get(0).getLots()) {
                lotNames.add(lot.getName());
                lotLevels.add(lot.getLevel());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lotNames);
            binding.layoutCheckOutDetail.spinnerParkingLots.setAdapter(adapter);
        }*/


        binding.layoutCheckOutDetail.spinnerParkingLots.setText(session.getParkingArea().get(Constants.KEY_PARKING_AREA), false);
        binding.layoutCheckOutDetail.spinnerParkingLots.setFreezesText(false);
        binding.layoutCheckOutDetail.tvFloorInfo.setText(session.getFloorLevel().get(Constants.KEY_FLOOR_LEVEL));

        binding.layoutCheckOutDetail.spinnerParkingLots.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.layoutCheckOutDetail.spinnerParkingLots.showDropDown();
                return false;
            }
        });

        binding.layoutCheckOutDetail.spinnerParkingLots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.layoutCheckOutDetail.tvFloorInfo.setText(lotLevels.get(position));

                //TODO pastiin ini denah nya atau bukan
//                byte[] imageByteArray = Base64.decode(selectableItems.get(0).getLots().get(position).getImage(), Base64.DEFAULT);
//                Glide.with(requireActivity())
//                        .load(imageByteArray)
//                        .into(binding.ivIndoorMap);
            }
        });

        binding.layoutCheckOutDetail.btnCheckOut.setOnClickListener(view -> {
            doShowDialogCheckOut();
        });

        binding.btnIndoorMap.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                binding.layoutIvIndoorMap.setVisibility(View.VISIBLE);
                binding.layoutTextDirectionIndoorMap.setVisibility(View.GONE);
//                String url_indoor_map = "https://images.squarespace-cdn.com/content/v1/55f3f914e4b05f0b68c2d673/1588832769264-X3YU3P2D8SNFCJ0B95MW/ke17ZwdGBToddI8pDm48kJuvXe6WwJaVdVxgKQ46ki0UqsxRUqqbr1mOJYKfIPR7LoDQ9mXPOjoJoqy81S2I8N_N4V1vUb5AoIIIbLZhVYxCRW4BPu10St3TBAUQYVKcmEytBQn_PkgFhjWWi94bkr8101QNyiQJXjcGrJSquml7lFzv6Tcm6Oz2A6SB1geL/Car%2BPark%2BAllocations.jpg";
//
//                RequestOptions requestOptions = new RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true);
//                Glide.with(requireContext())
//                        .load(url_indoor_map)
//                        .apply(requestOptions)
//                        .transition(withCrossFade())
//                        .into(binding.ivIndoorMap);


                binding.ivIndoorMap.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent e) {
                        if (e.getAction() == MotionEvent.ACTION_DOWN) {
//                    float x = event.getX() + imageView.getLeft();
//                    float y = event.getY() + imageView.getTop();

                            // get the surfaceView's location on screen
                            int[] loc = new int[2];
                            binding.ivIndoorMap.getLocationOnScreen(loc);
// calculate delta
                            int x = (int) (e.getRawX() - loc[0]);
                            int y = (int) (e.getRawY() - loc[1]);
                            binding.tvCoordinate.setText(x + " , " + y);
                        }
                        return false;
                    }
                });
            } else {
                binding.layoutIvIndoorMap.setVisibility(View.GONE);
                binding.layoutTextDirectionIndoorMap.setVisibility(View.VISIBLE);
            }
        });

        binding.layoutCheckOutDetail.btnMallInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).getBinding().bottomNavigation.setSelectedItemId(R.id.menu_mall);
            }
        });

    }

    private void funcLayoutCheckIn() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.layoutCheckInDetail.rvParkingLot.setLayoutManager(layoutManager);

        BackgroundHelper.backgroundTask(progress, () -> {
//            selectableItems = ParkingLotRepository.getInstance(requireContext()).generateItems(1);
            selectableItems = ParkingLotRepository.getInstance(requireContext()).generateListMall();
            return selectableItems;
        }, new CallbackOnResult<List<Mall>>() {
            @Override
            public void onFinish(List<Mall> result) {
                adapter = new SelectableParkingLotAdapter(ParkingFragment.this, selectableItems, item -> {
//                    binding.layoutCheckInDetail.btnCheckIn.setEnabled(inLocation(item));
//                    binding.layoutCheckInDetail.btnCheckIn.setText(inLocation(item) ? "CHECK IN" : "NOT IN RANGE");
//                    binding.layoutCheckInDetail.btnCheckIn.setBackgroundColor(inLocation(item) ?
//                            getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.transparentPrimary));
                });

                binding.layoutCheckInDetail.rvParkingLot.setAdapter(adapter);
            }

            @Override
            public void onFailed() {

            }
        });


        binding.layoutCheckInDetail.btnCheckIn.setOnClickListener(view1 -> {
            //TODO tambah dialog untuk validasi
            if(inParkingAreaRadius()){
                doCheckIn();
            }else{
                AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("No nearby parking lots were found")
                        .setMessage("Do you still want to check in")
                        .setPositiveButton("CHECK IN", null)
                        .setNegativeButton("CANCEL", null)
                        .show();

                Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                posButton.setOnClickListener(view -> {

                    doCheckIn();
                    dialog.dismiss();
                });
            }


        });
    }

    private void doCheckIn(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ParkingFragment.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QR code to check in");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

//        bypass qrcode
//        qrCode = "Parking Area\n1\n1".split("\n");
//        PARAM = 3;
//        new ConnectionOdoo().execute();
//        progress.show();

        //ini gausa
//        doShowDialogCheckInSuccess();
//        showCheckOutDetail(true);
//        funcLayoutCheckOut();
    }

    private boolean inLocation(SelectableParkingLot item) {
        //todo validasi lang lot

        return item.getName().toLowerCase().contains("Vivocity".toLowerCase());
    }

    private Boolean inParkingAreaRadius(){
        String lat, longi;

        Location currentLoc = new Location("");

        if(gpsTracker == null){
            gpsTracker = new GPSTracker(requireContext());
        }
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            currentLoc.setLatitude(latitude);
            currentLoc.setLongitude(longitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }

        if(listMall != null && !listMall.isEmpty()){
            for(int i = 0; i < listMall.size(); i++){
                if(listMall.get(i).getLatitude() != null && listMall.get(i).getLongitude() != null){

                    Location center = new Location("");
                    try{
                        center.setLatitude(Double.parseDouble(listMall.get(i).getLatitude()));
                        center.setLongitude(Double.parseDouble(listMall.get(i).getLongitude()));

                        float distanceInMeters = center.distanceTo(currentLoc);
                        boolean isWithin100m = distanceInMeters < 100;

                        if(isWithin100m){
                            return true;
                        }else{
                            if(i==listMall.size() - 1){
                                return false;
                            }
                        }
                    }catch (Exception e){

                    }
                }
            }
        }

        return false;
    }

    private void setMap() {
        try {
            boolean gps = false;
            gps = getMyLocation();

            if (!gps) {
                gpsTracker.showSettingsAlert();
            } else {
                // Gets to GoogleMap from the MapView and does initialization stuff
                binding.mapView.getMapAsync(this);
            }
        } catch (Exception ignored) {

        }
    }

    private void doShowDialogCheckInSuccess() {
        DialogCheckInSuccessBinding dialogCheckInSuccessBinding = DialogCheckInSuccessBinding
                .inflate(getLayoutInflater(), binding.getRoot(), false);

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setView(dialogCheckInSuccessBinding.getRoot())
                .setCancelable(true)
                .show();

        dialogCheckInSuccessBinding.getRoot().setOnClickListener(view -> dialog.dismiss());

    }

    private void doShowDialogCheckOut() {
        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle("CHECK OUT")
                .setMessage("Do you want to check out")
                .setPositiveButton("CHECK OUT", null)
                .setNegativeButton("CANCEL", null)
                .show();

        Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        posButton.setOnClickListener(view -> {
//            session.checkOut();
//
//            binding.btnIndoorMap.setChecked(false);
//            showCheckOutDetail(false);
//            funcLayoutCheckIn();

            PARAM = 5;
            new ConnectionOdoo().execute();
            progress.show();
            dialog.dismiss();
        });
    }

    private void showCheckOutDetail(boolean checkOut) {
        binding.layoutCheckInDetail.getRoot().setVisibility(checkOut ? View.GONE : View.VISIBLE);

        binding.layoutCheckOutDetail.getRoot().setVisibility(checkOut ? View.VISIBLE : View.GONE);
//        binding.btnIndoorMap.setVisibility(checkOut ? View.VISIBLE : View.GONE);

//        binding.layoutTextDirectionIndoorMap.setVisibility(checkOut ? View.VISIBLE : View.GONE);
        binding.layoutIvIndoorMap.setVisibility(checkOut ? View.VISIBLE : View.GONE);

    }

    private class ConnectionOdoo extends AsyncTask<Void, String, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                if (PARAM == 0) {
                    data = new ArrayList<>();

                    data = oc.search_read(
                            "looq_mall.mall",//api
                            new Object[]
                                    {new Object[]//conditions/parameter
                                            {}
                                    }
                            , Constants.GENERAL
                            , Constants.FIELDS_MALL);
                    if (data != null) {
                        JSONArray jsonArray = new JSONArray(data);
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Mall>>() {
                        }.getType();
                        listMall = new ArrayList<>();
                        listMall = gson.fromJson(String.valueOf(jsonArray), type);

                        Helper.setItemParam(Constants.ALREADY_LOADED, "1");
                        Helper.setItemParam(Constants.LIST_MALL, listMall);
                    }
                    return true;
                } else if (PARAM == 1) {
                    for(Mall temp : listMall) {
                        data = new ArrayList<>();
                        data = oc.search_read(
                                "looq_mall.parking_area",//api
                                new Object[]
                                        {new Object[]//conditions/parameter
                                                {new Object[]{"mall_id", "=", temp.getId()}}
                                        }
                                , Constants.GENERAL
                                , Constants.FIELDS_PARKING_AREA);
                        if (data != null) {
                            JSONArray jsonArray = new JSONArray(data);
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<ParkingArea>>() {
                            }.getType();
                            if (listParkingArea != null && listParkingArea.size() != 0) {
                                listParkingArea.add(gson.fromJson(String.valueOf(jsonArray), type));
                            } else {
                                listParkingArea = new ArrayList<>();
                                listParkingArea = gson.fromJson(String.valueOf(jsonArray), type);
                            }
                        }
                    }
                    return true;
                } else if(PARAM == 2) {
//                    for(Mall temp : listMall) {
                    data = new ArrayList<>();
                    data = oc.search_read(
                            "looq_mall.transaction_ads",//api
                            new Object[]
                                    {new Object[]//conditions/parameter
//                                                {new Object[]{"mall", "=", temp.getId()}}
                                            {new Object[]{"mall", "=", session.getMallId().get(Constants.KEY_MALL_ID)}}
                                    }
                            , Constants.GENERAL
                            , Constants.FIELDS_EVENTS);
                    if (data != null && data.size() != 0) {
                        JSONArray jsonArray = new JSONArray(data);
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Event>>() {
                        }.getType();

                        if (listAllEvent != null && listAllEvent.size() != 0) {
                            listAllEvent.add(gson.fromJson(String.valueOf(jsonArray), type));
                        } else {
                            listAllEvent = new ArrayList<>();
                            listAllEvent = gson.fromJson(String.valueOf(jsonArray), type);
                        }
                    }
//                    }
                    listPromotion = new ArrayList<>();
                    listEvent = new ArrayList<>();
                    if (listAllEvent != null && !listAllEvent.isEmpty()) {
                        for (int i = 0; i < listAllEvent.size(); i++) {
                            if (listAllEvent.get(i).getType().equals(Constants.PROMOTION_TYPE_CODE)) {
                                listPromotion.add(listAllEvent.get(i));
                            } else {
                                listEvent.add(listAllEvent.get(i));
                            }
                        }
                    }
                    Helper.setItemParam(Constants.LIST_PROMOTION, listPromotion);
                    Helper.setItemParam(Constants.LIST_EVENT, listEvent);
                    return true;
                }else if(PARAM == 3){
                    data = new ArrayList<>();
                    data = oc.search_read(
                            "looq_mall.parking_area",//api
                            new Object[]
                                    {new Object[]//conditions/parameter
                                            {
                                                new Object[]{"name", "=", qrCode[0]},
                                                new Object[]{"mall_id", "=", Integer.parseInt(qrCode[1])}}
                                    }
                            , Constants.GENERAL_1
                            , Constants.FIELDS_PARKING_AREA);
                    if (data != null) {
                        JSONArray jsonArray = new JSONArray(data);
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<ParkingArea>>() {
                        }.getType();
                        if (listParkingArea != null && listParkingArea.size() != 0) {
                            listParkingArea.add(gson.fromJson(String.valueOf(jsonArray), type));
                        } else {
                            listParkingArea = new ArrayList<>();
                            listParkingArea = gson.fromJson(String.valueOf(jsonArray), type);
                        }
                    }
                    listParking = new ArrayList<>();
                    for(ParkingLot temp : listParkingArea.get(0).getLots()) {
                        data = new ArrayList<>();
                        data = oc.search_read(
                                "looq_mall.parking_lot",//api
                                new Object[]
                                        {new Object[]//conditions/parameter
                                                {
                                                    new Object[]{"id", "=", temp.getId()},
                                                        new Object[]{"mall_id", "=", Integer.parseInt(qrCode[1])}
                                                        , new Object[]{"level", "=", qrCode[2]}}
                                        }
                                , Constants.GENERAL_1
                                , Constants.FIELDS_PARKING_LOT);
                        if (data != null) {
                            JSONArray jsonArray = new JSONArray(data);
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Parking>>() {
                            }.getType();
                            listParking.add(((List<Parking>)gson.fromJson(String.valueOf(jsonArray), type)).get(0));
                        }
                    }
                    Helper.setItemParam(Constants.PARKING, listParking);
                    new SessionManager(requireContext())
                            .createMallId(Integer.parseInt(qrCode[1]),
                                    qrCode[0], qrCode[2], listParking);
                    return true;
                }else if(PARAM == 4){
                    dataResult = new HashMap<>();
                    dataResult = oc.call("looq_mall.transaction_parking", "set_check_in",
                            new Object[]
                                    {cd.getUid(), latitude, longitude, listParking.get(0).getId()});
                    return true;
                }else{
                    Map<String, String> checkinSession = session.getCheckin();
                    String checkin = checkinSession.get(Constants.KEY_CHECKIN);
//                    dataResult = new HashMap<>();
                    dataResult = oc.call("looq_mall.transaction_parking", "set_check_out",
                            new Object[]
                                    {Integer.parseInt(checkin)});
                    return true;
                }

            } catch (Exception e) {
                error = OdooConnect.testConnection(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
                e.printStackTrace();
                errorMessage = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (PARAM == 0) {
                if (result) {
                    if (listMall != null && listMall.size() != 0) {
//                        PARAM = 2;
//                        new ConnectionOdoo().execute();
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        if (error) {
                            if(errorMessage.equals("Attempt to invoke virtual method 'java.util.List sg.carpark.looq.utils.helper.OdooConnect.search_read(java.lang.String, java.lang.Object[], java.lang.String, java.lang.String[])' on a null object reference")){
                                Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
                                logOut();
                            }else {
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
                            logOut();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection error when get mall", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            } else if (PARAM == 1) {
                if (result) {
                    if (listParkingArea != null && listParkingArea.size() != 0) {
                        progress.dismiss();
                        PARAM = 2;
                        new ConnectionOdoo().execute();
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        if (error) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
                            logOut();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection error when get parking area", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            }else if(PARAM == 2){
//                doShowDialogCheckInSuccess();
//                funcLayoutCheckOut();
//                showCheckOutDetail(true);
                if (result) {
                    if (listAllEvent != null && listAllEvent.size() != 0) {
                        progress.dismiss();
                        //do something
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        if (error) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
                            logOut();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection error when get event", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            }else if(PARAM == 3){
                if (result) {
                    if (listParking != null && listParking.size() != 0) {
                        PARAM = 4;
                        new ConnectionOdoo().execute();
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        if (error) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
                            logOut();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection error when get event", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            }else if(PARAM == 4){
                progress.dismiss();
                if (result) {
                    if (dataResult != null && dataResult.size() != 0) {
                        session.createCheckin(dataResult.get("id").toString());
                        doShowDialogCheckInSuccess();
                        funcLayoutCheckOut();
                        showCheckOutDetail(true);
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        if (error) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
                            logOut();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection error when get event", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            }else{
                progress.dismiss();
                if (result) {
                    if (dataResult != null && dataResult.size() != 0) {
                        session.checkOut();
                        binding.btnIndoorMap.setChecked(false);
                        showCheckOutDetail(false);
                        funcLayoutCheckIn();
                    } else {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        if (error) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
                            logOut();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Connection error when get event", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            }
        }

    }
}
