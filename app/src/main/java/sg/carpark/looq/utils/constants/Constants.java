package sg.carpark.looq.utils.constants;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

public class Constants {
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final String AUTHORIZATION_LOGIN = "Basic V0VCX0NMSUVOVDpXRUJfQ0xJRU5U";
    public static final String BEARER = "Bearer ";
    public static final String HTTP_HEADER_CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String OAUTH_TOKEN_PATH = "oauth/token";
    public static final String GRANT_TYPE = "grant_type=password&username=";
    public static final String _GRANT_TYPE = "|000&password=";
    public static final String TOKEN = "TOKEN";
    public static final String ERROR_LOGIN = "errorLogin";
    public static final String IS_LOGIN = "isLogin";

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String BACKGROUND_EXCEPTION = "BACKGROUND_EXCEPTION" ;

    public static final String DATA_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DISPLAY_DATE_FORMAT = "EEE, dd MMM yyyy";
    public static final String DATE_FORMAT = "dd MMM yyyy";

    public static final String BASE_URL = "http://192.168.100.7:8080";

    public static final String API_OAUTH_TOKEN = "/oauth/token";
    public static final String API_GET_MATERIAL = "/api/v1/material/getMasterData?idSloc=3010&idPlant=3112";
    public static final String API_GET_MATERIAL_TYPE = "/api/v1/material_type/getMasterData";

    public static final String API_PREFIX = "/api/v1/";

    public static final String API_GET_EMPLOYEE_DETAIL = "employee/getDetail";

    public static final String API_POST_CHANGE_PASSWORD = "employee/changePassword";


    public static final String EQUALS = "=";
    public static final String QUESTION = "?";
    public static final String AND = "&";
    public static final String TXTPASSWORD = "password";
    public static final String USERNAME = "username";

    public static final int PORT = 8069;
    public static final String DOMAIN = "zenexodoo.ddns.net";
    public static final String DB = "looq";
    public static final String GENERAL = "name desc";
    public static final String GENERAL_1 = "name";

    public static final String[] FIELDS_PARKING_LOT = {"area_id", "attachment_ids_new","closest_lift_lobby" ,"mall_id", "id", "level", "name"};
    public static final String[] FIELDS_MALL = {"id", "name", "latitude", "longitude", "location", "parking_areas_ids", "tenant_ids" };
    public static final String[] FIELDS_PARKING_AREA = {"id", "lots_ids", "mall_id", "name"};
    public static final String[] FIELDS_EVENTS = {"id", "mall", "tenant", "name","ads_image", "ads_title", "ads_description", "ads_html", "type", "date_start", "date_end"};
//    public static final String[] FIELDS_EVENTS = {"id", "mall", "tenant", "name", "type", "date_start", "date_end", "description"};
    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_USER = "KEY_USER";
    public static final String KEY_OC = "KEY_OC";
    public static final String KEY_CHECKIN = "KEY_CHECKIN";

    public static final String KEY_MALL_ID = "KEY_MALL_ID";
    public static final String KEY_PARKING_AREA = "KEY_PARKING_AREA";
    public static final String KEY_FLOOR_LEVEL = "KEY_FLOOR_LEVEL";


    public static final String SELECTED_ITEM = "SELECTED_ITEM";
    public static final String PARKING_LOTS = "PARKING_LOTS";

    public static final String PROMOTION_TYPE_CODE = "1";
    public static final String EVENT_TYPE_CODE = "2";

    public static final String LIST_PROMOTION = "list promotion";
    public static final String LIST_EVENT = "list event";
    public static final String LIST_MALL = "LIST_MALL";
    public static final String PARKING = "PARKING";
    public static final String ALREADY_LOADED = "ALREADY LOADED";
    public static final String KEY_ATTACHMENT = "KEY_ATTACHMENT";
    public static final String SELECTED_EVENT = "SELECTED_EVENT";
}
