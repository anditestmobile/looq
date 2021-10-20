package sg.carpark.looq.ui.userpoints;

import android.os.Bundle;

import java.util.ArrayList;

import sg.carpark.looq.data.model.BasePoints;
import sg.carpark.looq.databinding.ActivityUserPointsBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.detail.DetailAdapter;
import sg.carpark.looq.ui.mall.adapter.PagerAdapter;
import sg.carpark.looq.ui.mall.viewmodel.PromotionViewModel;

/**
 * Created by TED on 03-Dec-20
 */
public class UserPointsActivity extends BaseActivity {
    private ActivityUserPointsBinding binding;
    private DetailAdapter adapter;
    private PromotionViewModel promotionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserPointsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        binding.layoutUserPoints.tvGreeting.setText("HELLO, "+user.getName());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());

        setupViewPager();
        setupTabLayout();

        if(user != null && user.getName() != null){
            binding.layoutUserPoints.tvGreeting.setText("Hello, " + user.getName());
        }
    }

    private void setupViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(this);
        binding.layoutUserPoints.viewPagerHistory.setAdapter(pagerAdapter);
    }

    private void setupTabLayout() {
        ArrayList<BasePoints> list = new ArrayList<>();
        BasePoints base = new BasePoints();
        base.setTypeName("Promotion");
        list.add(base);

        BasePoints base2 = new BasePoints();
        base2.setTypeName("Events");
        list.add(base2);

        /*new TabLayoutMediator(binding.tabLayout, binding.viewPagerHistory,
                (tab, position) -> tab.setText(list.get(position).getTypeName())
        ).attach();*/

    }
}