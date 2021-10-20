package sg.carpark.looq.ui.changepassword;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import sg.carpark.looq.R;
import sg.carpark.looq.databinding.ActivityChangePasswordBinding;
import sg.carpark.looq.databinding.DialogCheckInSuccessBinding;
import sg.carpark.looq.databinding.DialogConfirmationBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;

/**
 * Created by TED on 30-Jul-20
 */
public class ChangePasswordActivity extends BaseActivity {

    ActivityChangePasswordBinding binding;
    protected Dialog dialog;
    Object results = new Object();
    HashMap<String, Object> password = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        init();

        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        binding.btnSave.setOnClickListener(view -> {
            String message = null;
            String oldPassword = binding.edtOldPassword.getText() != null ? binding.edtOldPassword.getText().toString() : null;
            String newPassword = binding.edtNewPassword.getText() != null ? binding.edtNewPassword.getText().toString() : null;
            String confirmPassword = binding.edtReTypePassword.getText() != null ? binding.edtReTypePassword.getText().toString() : null;

            if (oldPassword != null && oldPassword.isEmpty()) {
                message = getResources().getString(R.string.change_password_message_empty_old_password);
            } else if (newPassword != null && newPassword.isEmpty()) {
                message = getResources().getString(R.string.change_password_message_empty_new_password);
            } else if (confirmPassword != null && confirmPassword.isEmpty()) {
                message = getResources().getString(R.string.change_password_message_empty_confirm_password);
            } else if (confirmPassword != null && !confirmPassword.equals(newPassword)) {
                message = getResources().getString(R.string.change_password_message_new_do_not_match_confirm);
            }

            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                showDialogChangePassword();
            }
        });

    }

    private void showDialogChangePassword() {
        if (dialog == null || !dialog.isShowing()) {

            DialogConfirmationBinding dialogBinding = DialogConfirmationBinding
                    .inflate(getLayoutInflater(), binding.getRoot(), false);


            AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                    .setView(dialogBinding.getRoot())
                    .setCancelable(true)
                    .show();


            dialogBinding.btnNo.setOnClickListener(view1 -> dialog.dismiss());
            dialogBinding.btnYes.setOnClickListener(view1 -> {

                doChangePassword();
            });


            dialogBinding.getRoot().setOnClickListener(view -> dialog.dismiss());
        }
    }

    private void doChangePassword() {
//        showDialogSuccess();
        new ConnectionOdoo().execute();
        progress.show();
    }

    private void showDialogSuccess() {
        if (dialog == null || !dialog.isShowing()) {
            DialogCheckInSuccessBinding dialogBinding = DialogCheckInSuccessBinding.inflate(getLayoutInflater(), binding.getRoot(), false);

            dialogBinding.tvMessage.setText(getResources().getString(R.string.change_password_dialog_success_message));
            AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                    .setView(dialogBinding.getRoot())
                    .setCancelable(true)
                    .show();

            dialog.setOnDismissListener(dialogInterface -> onBackPressed());

            dialogBinding.getRoot().setOnClickListener(view -> {
                dialog.dismiss();

                onBackPressed();
            });

        }
    }

    private class ConnectionOdoo extends AsyncTask<Void, String, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                password = new HashMap<String, Object>();
                password.put("password", binding.edtNewPassword.getText().toString());
                results = oc.write("res.users", new Integer[]{cd.getUid()}, password);
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
        }

        @Override
        protected void onPostExecute(Boolean result) {

            progress.dismiss();
            if ((Boolean) results) {
                showDialogSuccess();
                cd.setPassword(binding.edtNewPassword.getText().toString());
                finish();
            } else {
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Connection error when change password", Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            }

        }
    }
}