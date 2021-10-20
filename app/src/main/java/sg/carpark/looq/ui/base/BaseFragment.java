package sg.carpark.looq.ui.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sg.carpark.looq.data.model.ConnectionData;
import sg.carpark.looq.data.model.User;
import sg.carpark.looq.ui.login.LoginActivity;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;
import sg.carpark.looq.utils.helper.OdooConnect;
import sg.carpark.looq.utils.session.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;

import sg.carpark.looq.R;

import java.util.Map;

/**
 * Created by TED on 28-Jul-20
 */
public class BaseFragment extends Fragment {
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    protected Dialog dialog;
    protected MaterialAlertDialogBuilder materialDialog;
    protected ViewGroup rootView;

    protected ConnectionData cd;
    protected OdooConnect oc;
    protected User user;
    protected SessionManager session;
    protected ProgressDialog progress;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    protected void init(){
        setSessionUser();
        cd = (ConnectionData) Helper.getItemParam(Constants.KEY_DATA);
        oc = (OdooConnect) Helper.getItemParam(Constants.KEY_OC);
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading. Please wait...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    private void setSessionUser() {
        session = new SessionManager(getActivity());
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

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void initRecycleView(RecyclerView recyclerView, RecyclerView.Adapter mAdapter, boolean dividerDecoration) {
        if (dividerDecoration) {
            DividerItemDecoration itemDecor = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecor);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    public void initRecycleView(RecyclerView recyclerView, RecyclerView.Adapter mAdapter, int minColumn) {
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), minColumn));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    public void hideBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(view.getHeight()).setDuration(300);
        view.setVisibility(View.GONE);
    }

    public void showBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(0).setDuration(300);
        view.setVisibility(View.VISIBLE);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected MaterialSharedAxis createXSharedTransition(boolean entering) {
        int axis = MaterialSharedAxis.X;

        MaterialSharedAxis transition = new MaterialSharedAxis(axis, entering);

        return transition;
    }

    protected MaterialFadeThrough createFadeTransition(){
        MaterialFadeThrough fadeThrough = new MaterialFadeThrough();

        return fadeThrough;
    }

    public void stackFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }
    public void inclusiveStackFragment(Fragment fragment) {
        // Pop off everything up to and including the current tab
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        // Add the new tab fragment
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit();
    }

}
