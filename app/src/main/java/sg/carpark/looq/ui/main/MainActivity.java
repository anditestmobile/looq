package sg.carpark.looq.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import sg.carpark.looq.R;
import sg.carpark.looq.data.model.Parking;
import sg.carpark.looq.databinding.ActivityMainBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.mall.MallFragment;
import sg.carpark.looq.ui.parking.ParkingFragment;
import sg.carpark.looq.ui.profile.ProfileFragment;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public ActivityMainBinding getBinding() {
        return binding;
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        Helper.removeItemParam(Constants.ALREADY_LOADED);


        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        loadFragment(new ParkingFragment());

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isCheckedIn(){
        //todo ganti true dengan validasi
        boolean result = session.isMallIdExist();

        return result;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_parking:
                fragment = new ParkingFragment();
                break;
            case R.id.menu_mall:
                if(isCheckedIn()){
                    fragment = new MallFragment();
                }else{
                    Toast.makeText(MainActivity.this, "Please check-in first", Toast.LENGTH_SHORT).show();
                    binding.bottomNavigation.setSelectedItemId(R.id.menu_parking);
                }
                break;
            case R.id.menu_profile:
                fragment = new ProfileFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return fragment != null && loadFragment(fragment);
    }
}