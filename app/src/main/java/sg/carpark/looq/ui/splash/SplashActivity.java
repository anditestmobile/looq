package sg.carpark.looq.ui.splash;
/**
 * Created by TED on 18-Jul-20
 */


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import sg.carpark.looq.data.model.ConnectionData;
import sg.carpark.looq.data.model.User;
import sg.carpark.looq.ui.main.MainActivity;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.Map;

import sg.carpark.looq.databinding.ActivitySplashBinding;
import sg.carpark.looq.ui.login.LoginActivity;
import sg.carpark.looq.utils.session.SessionManager;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private SessionManager session;
    ConnectionData cd = new ConnectionData();
    User user = new User();
    OdooConnect oc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        session = new SessionManager(getApplicationContext());

        String[] permissions = {
                Manifest.permission.INTERNET, Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.VIBRATE, Manifest.permission.READ_EXTERNAL_STORAGE
        };

        Permissions.check(this, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                new Handler().postDelayed(() -> {
                    checkLogin();
                    finish();
                }, 1000);
            }

            private void checkLogin() {
//                Intent i;
////                if (sessionManager.getToken() != null) {
////                    //klo uda pernah login sebelumnya, lgsung masuk ke aplikasi
////                    i = new Intent(SplashActivity.this, MainActivity.class);
////                } else {
//                    //kalo session baru, login dlu save token ke session
//                    i = new Intent(SplashActivity.this, LoginActivity.class);
////                }
//
//                //uncomment kalo uda ada uncomment yang diatas
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);

                if (session.isDataExist() && session.isUserExist()) {
                    Map<String, String> dataSession = session.getDataDetail();
                    String mData = dataSession.get(Constants.KEY_DATA);
                    cd = (ConnectionData) Helper.stringToObject(mData);
                    Helper.setItemParam(Constants.KEY_DATA, cd);

                    Map<String, String> userSession = session.getUserDetail();
                    String mUser = userSession.get(Constants.KEY_USER);
                    user = (User) Helper.stringToObject(mUser);
                    Helper.setItemParam(Constants.KEY_USER, user);

                    Map<String, String> ocSession = session.getOC();
                    String mOC = ocSession.get(Constants.KEY_OC);
                    oc = (OdooConnect) Helper.stringToObject(mOC);
                    Helper.setItemParam(Constants.KEY_OC, oc);

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                finish();
            }
        });
    }

}
