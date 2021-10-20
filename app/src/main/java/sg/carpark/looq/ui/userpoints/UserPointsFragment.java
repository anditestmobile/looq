package sg.carpark.looq.ui.userpoints;/*
package sg.carpark.looq.ui.userpoints;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import sg.carpark.looq.R;
import sg.carpark.looq.data.model.BaseFeatured;
import sg.carpark.looq.data.model.BasePoints;
import sg.carpark.looq.databinding.FragmentProfileBinding;
import sg.carpark.looq.databinding.FragmentUserPointsBinding;
import sg.carpark.looq.ui.base.BaseFragment;
import sg.carpark.looq.ui.login.LoginActivity;
import sg.carpark.looq.ui.main.MainActivity;
import sg.carpark.looq.ui.mall.adapter.PagerAdapter;
import sg.carpark.looq.utils.helper.Helper;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

*/
/**
 * Created by TED on 23-Nov-20
 *//*

public class UserPointsFragment extends BaseFragment {
    private FragmentUserPointsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(createXSharedTransition(true));
        setReenterTransition(createXSharedTransition(false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).getBinding().toolbarTitle.setText("MY POINTS");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserPointsBinding.inflate(inflater, container, false);


        setupViewPager();
        setupTabLayout();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void setupViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(this);
        binding.viewPagerHistory.setAdapter(pagerAdapter);
    }

    private void setupTabLayout() {
        ArrayList<BasePoints> list = new ArrayList<>();
        BasePoints base = new BasePoints();
        base.setTypeName("Promotion");
        list.add(base);

        BasePoints base2 = new BasePoints();
        base2.setTypeName("Events");
        list.add(base2);

        */
/*new TabLayoutMediator(binding.tabLayout, binding.viewPagerHistory,
                (tab, position) -> tab.setText(list.get(position).getTypeName())
        ).attach();*//*


    }
}
*/
