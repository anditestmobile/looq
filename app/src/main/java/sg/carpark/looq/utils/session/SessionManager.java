package sg.carpark.looq.utils.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import sg.carpark.looq.data.model.Parking;
import sg.carpark.looq.data.model.ParkingQR;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by TED on 29-Sep-20
 */
public class SessionManager {
    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences prefUser;
    private SharedPreferences prefOC;
    private SharedPreferences prefCheckin;

    private SharedPreferences prefMallId;
    private SharedPreferences prefParkingArea;
    private SharedPreferences prefFloorLevel;
    private SharedPreferences prefAttachment;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editorUser;
    private SharedPreferences.Editor editorOC;
    private SharedPreferences.Editor editorCheckin;

    private SharedPreferences.Editor editorMallId;
    private SharedPreferences.Editor editorParkingArea;
    private SharedPreferences.Editor editorFloorLevel;
    private SharedPreferences.Editor editorAttachment;

    // Context
    private Context _context;

    // Sharedpref file name
    private static final String PREF_NAME = "SSPref";
    private static final String PREF_USER = "UserPref";
    private static final String PREF_OC = "UserOC";
    private static final String PREF_CHECKIN = "CheckIn";
    private static final String PREF_MALL_ID = "MallIdPref";
    private static final String PREF_PARKING_AREA = "ParkingAreaPref";
    private static final String PREF_FLOOR_LEVEL = "FloorLevelPref";
    private static final String PREF_ATTACHMENT = "AttachmentPref";

    private static final String IS_DATA = "isData";
    private static final String IS_USER = "isData";
    private static final String IS_OC = "isOC";
    private static final String IS_CHECKIN = "isCheckin";
    private static final String IS_ANY_MALL_ID = "isAnyMallId";
    private static final String IS_PARKING_AREA = "isParkingArea";
    private static final String IS_FLOOR_LEVEL = "isFloorLevel";
    private static final String IS_ATTACHMENT = "isAttachment";


    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        // Shared pref mode
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        prefUser = _context.getSharedPreferences(PREF_USER, PRIVATE_MODE);
        prefOC = _context.getSharedPreferences(PREF_OC, PRIVATE_MODE);
        prefCheckin = _context.getSharedPreferences(PREF_CHECKIN, PRIVATE_MODE);
        prefMallId = _context.getSharedPreferences(PREF_MALL_ID, PRIVATE_MODE);
        prefParkingArea = _context.getSharedPreferences(PREF_PARKING_AREA, PRIVATE_MODE);
        prefFloorLevel = _context.getSharedPreferences(PREF_FLOOR_LEVEL, PRIVATE_MODE);
        prefAttachment = _context.getSharedPreferences(PREF_ATTACHMENT, PRIVATE_MODE);

        editor = pref.edit();
        editorUser = prefUser.edit();
        editorOC = prefOC.edit();
        editorCheckin = prefCheckin.edit();
        editorMallId = prefMallId.edit();
        editorParkingArea = prefParkingArea.edit();
        editorFloorLevel = prefFloorLevel.edit();
        editorAttachment = prefAttachment.edit();
    }

    /**
     * Create login session
     */
    public void createUserProfile(String fileNameList) {
        editorUser.putBoolean(IS_USER, true);
        editorUser.putString(Constants.KEY_USER, fileNameList);
        editorUser.commit();
    }

    public void createOC(String oc) {
        editorOC.putBoolean(IS_OC, true);
        editorOC.putString(Constants.KEY_OC, oc);
        editorOC.commit();
    }

    public void createCheckin(String checkin) {
        editorCheckin.putBoolean(IS_CHECKIN, true);
        editorCheckin.putString(Constants.KEY_CHECKIN, checkin);
        editorCheckin.commit();
    }

    public void createFileSession(String fileNameList) {
        editor.putBoolean(IS_DATA, true);
        editor.putString(Constants.KEY_DATA, fileNameList);
        editor.commit();
    }

    public void createMallId(int mallId, String parkingArea, String floorLevel, List<Parking> attachments) {
        editorMallId.putBoolean(IS_ANY_MALL_ID, true);

        editorMallId.putInt(Constants.KEY_MALL_ID, mallId);
        editorParkingArea.putString(Constants.KEY_PARKING_AREA, parkingArea);
        editorFloorLevel.putString(Constants.KEY_FLOOR_LEVEL, floorLevel);
        Gson gson = new Gson();
        String jsonAttachment = gson.toJson(attachments);
//        Set<Object> att = new HashSet<>();
//        for(int i = 0 ; i < attachments.size() ; i++){
//            att.add(attachments.get(i).getData());
//        }
        editorAttachment.putString(Constants.KEY_ATTACHMENT, jsonAttachment);

        editorMallId.commit();
        editorParkingArea.commit();
        editorFloorLevel.commit();
        editorAttachment.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();

        editorUser.clear();
        editorUser.commit();

        editorOC.clear();
        editorOC.commit();

        editorCheckin.clear();
        editorCheckin.commit();

        editorMallId.clear();
        editorMallId.commit();

        editorParkingArea.clear();
        editorParkingArea.commit();

        editorFloorLevel.clear();
        editorFloorLevel.commit();

        editorAttachment.clear();
        editorAttachment.commit();
    }

    public void checkOut(){

        editorMallId.clear();
        editorMallId.commit();

        editorParkingArea.clear();
        editorParkingArea.commit();

        editorFloorLevel.clear();
        editorFloorLevel.commit();

        editorAttachment.clear();
        editorAttachment.commit();

        editorCheckin.clear();
        editorCheckin.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getDataDetail() {
        HashMap<String, String> fileDetail = new HashMap<>();
        // user name
        fileDetail.put(Constants.KEY_DATA, pref.getString(Constants.KEY_DATA, null));
        return fileDetail;
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> fileDetail = new HashMap<>();
        fileDetail.put(Constants.KEY_USER, prefUser.getString(Constants.KEY_USER, null));
        return fileDetail;
    }
    public HashMap<String, String> getOC() {
        HashMap<String, String> fileDetail = new HashMap<>();
        fileDetail.put(Constants.KEY_OC, prefOC.getString(Constants.KEY_OC, null));
        return fileDetail;
    }

    public HashMap<String, String> getCheckin() {
        HashMap<String, String> fileDetail = new HashMap<>();
        fileDetail.put(Constants.KEY_CHECKIN, prefCheckin.getString(Constants.KEY_CHECKIN, null));
        return fileDetail;
    }
    public HashMap<String, Integer> getMallId() {
        HashMap<String, Integer> fileDetail = new HashMap<>();
        fileDetail.put(Constants.KEY_MALL_ID, prefMallId.getInt(Constants.KEY_MALL_ID, 0));
        return fileDetail;
    }
    public HashMap<String, String> getParkingArea() {
        HashMap<String, String> fileDetail = new HashMap<>();
        fileDetail.put(Constants.KEY_PARKING_AREA, prefParkingArea.getString(Constants.KEY_PARKING_AREA, null));
        return fileDetail;
    }
    public HashMap<String, String> getFloorLevel() {
        HashMap<String, String> fileDetail = new HashMap<>();
        fileDetail.put(Constants.KEY_FLOOR_LEVEL, prefFloorLevel.getString(Constants.KEY_FLOOR_LEVEL, null));
        return fileDetail;
    }
    public HashMap<String, String> getAttachments() {
        HashMap<String, String> fileDetail = new HashMap<>();
        fileDetail.put(Constants.KEY_ATTACHMENT, prefAttachment.getString(Constants.KEY_ATTACHMENT, null));
        return fileDetail;
    }


//	public HashMap<String, String> getUrl(){
//		HashMap<String, String> loginDetail = new HashMap<>();
//		// user name
//		loginDetail.put(Constants.KEY_URL, pref.getString(Constants.KEY_URL, null));
//
//
//		return loginDetail;
//	}

    /**
     * Quick check for login
     **/
    public boolean isDataExist() {
        return pref.getBoolean(IS_DATA, false);
    }

    public boolean isUserExist() {
        return prefUser.getBoolean(IS_USER, false);
    }

    public boolean isOCExist() {
        return prefOC.getBoolean(IS_OC, false);
    }
    public boolean isCheckinExist() {
        return prefCheckin.getBoolean(IS_CHECKIN, false);
    }
    public boolean isMallIdExist() {
        if(getMallId().get(Constants.KEY_MALL_ID) != 0){
            return true;
        }else{
            return false;
        }
    }
}