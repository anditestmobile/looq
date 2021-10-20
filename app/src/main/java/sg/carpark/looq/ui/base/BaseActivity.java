package sg.carpark.looq.ui.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sg.carpark.looq.data.model.ConnectionData;
import sg.carpark.looq.data.model.User;
import sg.carpark.looq.ui.login.LoginActivity;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.BackgroundHelper;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;
import sg.carpark.looq.utils.session.SessionManager;

import java.util.Map;


/**
 * Created by TED on 03-Sep-20
 */
public class BaseActivity extends AppCompatActivity {

    protected Dialog dialog;
    protected ProgressDialog progress;
    protected ConnectionData cd;
    protected User user;
    protected OdooConnect oc;
    protected SessionManager session;
    protected String errorMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase();


        session = new SessionManager(getApplicationContext());
    }

    protected void initBase() {
        progress = BackgroundHelper.createProgressDialog(this);
    }

    public ProgressDialog getProgressDialog(){
        return progress;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
    public void initRecycleView(RecyclerView recyclerView, RecyclerView.Adapter mAdapter, boolean dividerDecoration) {
        if (dividerDecoration) {
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecor);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    protected void init(){
        setSessionUser();
        cd = (ConnectionData) Helper.getItemParam(Constants.KEY_DATA);
        oc = (OdooConnect) Helper.getItemParam(Constants.KEY_OC);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading. Please wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    private void setSessionUser() {
        session = new SessionManager(this);
        if(Helper.getItemParam(Constants.KEY_USER) == null){
            if (session.isUserExist()) {
                Map<String, String> userSession = session.getUserDetail();
                String mUser = userSession.get(Constants.KEY_USER);
                user = (User) Helper.stringToObject(mUser);
                Helper.setItemParam(Constants.KEY_USER, user);
            }else{
                logOut();
            }
        }else{
            user = (User) Helper.getItemParam(Constants.KEY_USER);
        }
    }

    public void logOut(){
        Helper.removeItemParam(Constants.KEY_DATA);
        Helper.removeItemParam(Constants.KEY_USER);
        Helper.removeItemParam(Constants.KEY_OC);
        session.clearSession();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
