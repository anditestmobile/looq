package sg.carpark.looq.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import sg.carpark.looq.databinding.ActivitySignUpBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.login.LoginActivity;

public class SignUpActivity extends BaseActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.labelSignIn.setOnClickListener(view -> {
            hideSoftKeyboard(binding.getRoot());
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        binding.btnSignUp.setOnClickListener(view -> {
            hideSoftKeyboard(binding.getRoot());

            Toast.makeText(this, "Please check your email for confirmation", Toast.LENGTH_SHORT).show();
        });
    }
}