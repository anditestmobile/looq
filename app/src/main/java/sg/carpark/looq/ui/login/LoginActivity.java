package sg.carpark.looq.ui.login;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.CoverPhoto;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import sg.carpark.looq.R;
import sg.carpark.looq.databinding.ActivitySignInBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.main.MainActivity;
import sg.carpark.looq.ui.signup.SignUpActivity;

public class LoginActivity extends BaseActivity implements LoginNavigator, GoogleApiClient.OnConnectionFailedListener {
    private ActivitySignInBinding binding;
    private LoginViewModel viewModel;
    private CallbackManager callbackManager;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    private String name, email;
    private String idToken;
    private String pass = "";
    private int FLAG_LOGIN = 0;
    private static final String TAG = "LoginActivity";
    private static final String EMAIL = "looqtest@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewModel();

        setupUiFunction();

        setupObserver();

        googleSignin();

        //todo
        //fb login
        callbackManager = CallbackManager.Factory.create();
        binding.loginBtnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //todo
//                if(loginResult != null){
//                    openMainActivity();
//                }

                String username = "api";
                String password = "Password1";

                hideKeyboard();
                viewModel.login(username, password);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                if(error != null){

                }
            }
        });

//        binding.loginBtnFacebook.setReadPermissions(Arrays.asList(EMAIL));


        binding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.loginBtnFacebook.performClick();
            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FLAG_LOGIN = 0;
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                googleApiClient.clearDefaultAccountAndReconnect();
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setupObserver() {
        viewModel.isLoading().observe(this, loading -> {
            if (loading != null && loading) {
                getProgressDialog().show();
            } else {
                getProgressDialog().dismiss();
            }
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(LoginViewModel.class);
        viewModel.setNavigator(this);
    }

    private void setupUiFunction() {
        binding.labelSignUp.setOnClickListener(view -> {
            hideKeyboard();

            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(view -> {
            String username = Objects.requireNonNull(binding.tilUsername.getEditText()).getText().toString();
            String password = Objects.requireNonNull(binding.tilPassword.getEditText()).getText().toString();

            hideKeyboard();
            viewModel.login(username, password);

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void googleSignin() {
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            name = account.getDisplayName();
            email = account.getEmail();
            pass = idToken.substring(0, 9);
            viewModel.signUp(name ,email, pass);
        }else{
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Login Unsuccessful. "+result);
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }


}