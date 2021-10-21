package sg.carpark.looq.ui.login;

import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import sg.carpark.looq.R;
import sg.carpark.looq.data.model.ConnectionData;
import sg.carpark.looq.data.model.User;
import sg.carpark.looq.data.repository.LoginRepository;
import sg.carpark.looq.ui.base.BaseViewModel;
import sg.carpark.looq.ui.signup.SignUpActivity;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;
import sg.carpark.looq.utils.session.SessionManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import io.reactivex.Completable;

import static sg.carpark.looq.utils.helper.Helper.isObjectInteger;

/**
 * Created by TED on 29-Nov-20
 */
public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    private final MutableLiveData<Boolean> loading;
    private final LoginRepository repository;

    //odoo
    ConnectionData cd = new ConnectionData();
    Boolean inp = false;
    AlertDialog.Builder msg;
    String msgResult = "";
    ConnectionOdoo tarea;
    int PARAM = 0;
    private OdooConnect oc;
    List<HashMap<String, Object>> data = new ArrayList<>();
    private int uId;
    private String errorMessage;
    private ArrayList<User> userProfile = new ArrayList<>();
    private ArrayList<User> partner = new ArrayList<>();
    private String name, username, password;
    private Object dataSignUp;
    private int result = 0;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = LoginRepository.getInstance();
        loading = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> isLoading() {
        return loading;
    }

    private boolean isEmailAndPasswordValid(String username, String password) {
        return !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
//        return true;
    }

    private boolean isNameEmailAndPasswordValid(String name, String username, String password) {
        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
//        return true;
    }

    public void signUp(String name, String email, String pass) {
        if (isNameEmailAndPasswordValid(name, email, pass)) {
            loading.postValue(true);
            this.name = name;
            username = email;
            password = pass;

            getCompositeDisposable().add(
                    Completable.complete()
                            .delay(1, TimeUnit.SECONDS)
                            .subscribe(() -> {
                                PARAM = 4;
                                tarea = new ConnectionOdoo();
                                tarea.execute();
                            })
            );
        } else {
            getNavigator().handleError(new Throwable(getApplication().getString(R.string.login_invalid)));
        }

    }

    public void login(String username, String password) {

        if (isEmailAndPasswordValid(username, password)) {
            loading.postValue(true);

            this.username = username;
            this.password = password;


            /*getCompositeDisposable().add(repository
                    .doCallOauthToken(new LoginRequest.ServerLoginRequest(username, password))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                                       @Override
                                       public void onSuccess(@io.reactivex.annotations.NonNull LoginResponse loginResponse) {
                                           loading.postValue(false);
                                           //todo save token to session
                                           getNavigator().openMainActivity();
                                       }

                                       @Override
                                       public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                           loading.postValue(false);
                                           getNavigator().handleError(e);
                                       }
                                   }
                    )
            );*/

            getCompositeDisposable().add(
                    Completable.complete()
                            .delay(1, TimeUnit.SECONDS)
                            .subscribe(() -> {
                                PARAM = 1;
                                tarea = new ConnectionOdoo();
                                tarea.execute();
                            })
            );

        } else {
            getNavigator().handleError(new Throwable(getApplication().getString(R.string.login_invalid)));
        }

    }

    private class ConnectionOdoo extends AsyncTask<Void, String, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // Try connection to server
            if (PARAM == 1) {
                if (inp) {
                    try {
                        Boolean ocT = OdooConnect.testConnection(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
                        if (ocT) {
                            uId = OdooConnect.getUserId(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
                            return true;
                        } else {
                            msgResult = "Connection error";
                        }
                    } catch (Exception ex) {
                        // Any other exception
                        msgResult = "Error: " + ex;
                    }
                }
            } else if (PARAM == 2) {
                try {
                    data = new ArrayList<>();
                    oc = OdooConnect.connect(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
                    data = oc.search_read(
                            "res.users",
                            new Object[]
                                    {new Object[]//conditions/parameter
                                            {new Object[]{"id", "=", uId}}
                                    }
                            , Constants.GENERAL, "partner_id", "company_id", "image_1920");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMessage = e.getMessage();
                    return false;
                }
            } else if (PARAM == 3) {
                try {
                    data = new ArrayList<>();
                    data = oc.search_read(
                            "res.partner",
                            new Object[]
                                    {new Object[]//conditions/parameter
                                            {new Object[]{"id", "=", Integer.valueOf(userProfile.get(0).getPartner_id()[0])}}
                                    }
                            , Constants.GENERAL
                            , "id", "name", "email", "phone");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMessage = e.getMessage();
                    return false;
                }
            } else {
                try {
                    Boolean ocT = OdooConnect.testConnection(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
                    if (ocT) {
                        uId = OdooConnect.getUserId(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
//                    return true;
                    } else {
                        msgResult = "Connection error";
                        return false;
                    }
                    HashMap map = new HashMap();
                    map.put("name", name);
                    map.put("login",username);
                    map.put("company_ids", new Object[]{1});
                    map.put("company_id", 1);
                    map.put("password", password);
                    oc = OdooConnect.connect(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
                    dataSignUp = oc.create(
                            "res.users",//api
                            map);
                    if(isObjectInteger(dataSignUp)){
                        result = (int) dataSignUp;
                    }else{
                        msgResult = (String) dataSignUp;
                        return false;
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMessage = e.getMessage();
                    return false;
                }
            }
            return false;
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPreExecute() {
            if (PARAM == 1) {
                cd.setUrl(Constants.DOMAIN);
                cd.setPort(Constants.PORT);
                cd.setDb(Constants.DB);
                cd.setUsername(username);
                cd.setPassword(password);
                inp = true;
            }else if(PARAM == 4){
                cd.setUrl(Constants.DOMAIN);
                cd.setPort(Constants.PORT);
                cd.setDb(Constants.DB);
                cd.setUsername(Constants.LOOQ_USER);
                cd.setPassword(Constants.LOOQ_PASSWORD);
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (PARAM == 1) {
                if (result) {
                    cd.setUid(uId);
                    PARAM = 2;
                    new ConnectionOdoo().execute();
                } else {
                    loading.postValue(false);
                    Toast.makeText(getApplication(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (data != null && data.size() != 0) {
                    JSONArray jsonArray = new JSONArray(data);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<User>>() {
                    }.getType();
                    if (PARAM == 2) {
                        userProfile = new ArrayList<>();
                        userProfile = gson.fromJson(String.valueOf(jsonArray), type);
                        PARAM = 3;
                        new ConnectionOdoo().execute();
                    } else {
                        partner = new ArrayList<>();
                        partner = gson.fromJson(String.valueOf(jsonArray), type);

                        executeGetData(null);
                    }
                } else if(PARAM == 3) {
                    loading.postValue(false);
                    Toast.makeText(getApplication(), "Could not get data from server", Toast.LENGTH_SHORT).show();
                } else{
                    if (result) {
//                        Toast.makeText(getApplication(), "Success to sign up", Toast.LENGTH_SHORT).show();
                        login(username, password);
                    } else {
                        if(msgResult.contains("You can not have two users with the same login!")){
                            login(username, password);
                        }else {
                            loading.postValue(false);
                            Toast.makeText(getApplication(), "Failed to login", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        }
    }

    public void executeGetData(View view) {
        userProfile.get(0).setEmail(partner.size() != 0 ? partner.get(0).getEmail() : null);
        userProfile.get(0).setName(partner.size() != 0 ? partner.get(0).getName() : null);
        userProfile.get(0).setPhone(partner.size() != 0 ? partner.get(0).getPhone() : null);
        String object = Helper.objectToString(cd);
        String user = Helper.objectToString(userProfile.get(0));
        String OdooConnect = Helper.objectToString(oc);

        new SessionManager(getApplication()).createFileSession(object);
        new SessionManager(getApplication()).createUserProfile(user);
        new SessionManager(getApplication()).createOC(OdooConnect);

        Helper.setItemParam(Constants.KEY_DATA, cd);
        Helper.setItemParam(Constants.KEY_USER, userProfile.get(0));
        Helper.setItemParam(Constants.KEY_OC, oc);
        loading.postValue(false);
        getNavigator().openMainActivity();
    }

}