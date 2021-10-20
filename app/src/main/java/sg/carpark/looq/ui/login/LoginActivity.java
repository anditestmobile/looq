package sg.carpark.looq.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import java.util.Objects;

import sg.carpark.looq.databinding.ActivitySignInBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.main.MainActivity;
import sg.carpark.looq.ui.signup.SignUpActivity;

public class LoginActivity extends BaseActivity implements LoginNavigator {
    private ActivitySignInBinding binding;
    private LoginViewModel viewModel;
    private  CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewModel();

        setupUiFunction();

        setupObserver();

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


        binding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.loginBtnFacebook.performClick();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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


}