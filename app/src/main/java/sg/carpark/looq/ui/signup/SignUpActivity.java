package sg.carpark.looq.ui.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import sg.carpark.looq.R;
import sg.carpark.looq.data.model.ConnectionData;
import sg.carpark.looq.data.model.Event;
import sg.carpark.looq.data.model.Mall;
import sg.carpark.looq.data.model.Parking;
import sg.carpark.looq.data.model.ParkingArea;
import sg.carpark.looq.data.model.ParkingLot;
import sg.carpark.looq.databinding.ActivitySignUpBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.login.LoginActivity;
import sg.carpark.looq.ui.login.LoginNavigator;
import sg.carpark.looq.ui.login.LoginViewModel;
import sg.carpark.looq.ui.main.MainActivity;
import sg.carpark.looq.ui.parking.ParkingFragment;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;
import sg.carpark.looq.utils.session.SessionManager;

import static sg.carpark.looq.utils.helper.Helper.isObjectInteger;

public class SignUpActivity extends BaseActivity {
    private ActivitySignUpBinding binding;
    private String name, username, password;
    private int uId;
    String msgResult = "";
    Object data;
    int result = 0;
    ConnectionData cd = new ConnectionData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progress = new ProgressDialog(this);
        progress.setMessage("Loading. Please wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        binding.labelSignIn.setOnClickListener(view -> {
            hideSoftKeyboard(binding.getRoot());
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        binding.btnSignUp.setOnClickListener(view -> {
            hideSoftKeyboard(binding.getRoot());
            signUp();
//            Toast.makeText(this, "Please check your email for confirmation", Toast.LENGTH_SHORT).show();
        });

    }

    private boolean isEmailAndPasswordValid(String name, String username, String password) {
        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password);
//        return true;
    }

    public void signUp() {
        name = Objects.requireNonNull(binding.tilName.getEditText()).getText().toString();
        username = Objects.requireNonNull(binding.tilUsername.getEditText()).getText().toString();
        password = Objects.requireNonNull(binding.tilPassword.getEditText()).getText().toString();
        if (isEmailAndPasswordValid(name, username, password)) {
            new ConnectionOdoo().execute();
            progress.show();
        }else{
            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.login_invalid), Toast.LENGTH_SHORT).show();
        }

    }

    private class ConnectionOdoo extends AsyncTask<Void, String, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
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
                data = oc.create(
                        "res.users",//api
                        map);
                if(isObjectInteger(data)){
                    result = (int) data;
                }else{
                    return false;
                }
                return true;
            } catch (Exception e) {
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
            cd.setUrl(Constants.DOMAIN);
            cd.setPort(Constants.PORT);
            cd.setDb(Constants.DB);
            cd.setUsername(Constants.LOOQ_USER);
            cd.setPassword(Constants.LOOQ_PASSWORD);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplication(), "Success to sign up", Toast.LENGTH_SHORT).show();
                openLoginActivity();
            } else {
                Toast.makeText(getApplication(), "Failed to sign up", Toast.LENGTH_SHORT).show();
            }
            progress.dismiss();
        }

    }

    public void openLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}