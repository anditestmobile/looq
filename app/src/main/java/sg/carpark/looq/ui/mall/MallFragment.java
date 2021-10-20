package sg.carpark.looq.ui.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import sg.carpark.looq.data.model.Event;
import sg.carpark.looq.data.repository.StoreDirectoryRepository;
import sg.carpark.looq.utils.helper.BackgroundHelper;
import sg.carpark.looq.utils.interfaces.CallbackOnResult;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.carpark.looq.data.model.BaseFeatured;
import sg.carpark.looq.data.model.Featured;
import sg.carpark.looq.data.repository.FeaturedRepository;
import sg.carpark.looq.databinding.FragmentMallBetaBinding;
import sg.carpark.looq.ui.base.BaseFragment;
import sg.carpark.looq.ui.detail.DetailActivity;
import sg.carpark.looq.ui.mall.adapter.FeaturedCompactAdapter;
import sg.carpark.looq.ui.mall.adapter.PagerAdapter;
import sg.carpark.looq.ui.mall.viewmodel.PromotionViewModel;
import sg.carpark.looq.ui.mall.viewmodel.StoreDirectoryViewModel;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;

/**
 * Created by TED on 23-Nov-20
 */
public class MallFragment extends BaseFragment {
    private FragmentMallBetaBinding binding;
    private FeaturedCompactAdapter adapter;
    private PromotionViewModel promotionViewModel;
    private StoreDirectoryViewModel storeDirectoryViewModel;
    private List<HashMap<String, Object>> data = new ArrayList<>();

    private List<Event> events = new ArrayList<>();
    private List<Event> listPreEvents = new ArrayList<>();
    private List<Featured> listEvents = new ArrayList<>();
    private List<Event> listPrePromotion = new ArrayList<>();
    private List<Featured> listPromotion = new ArrayList<>();
    private List<Featured> listStore = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(createXSharedTransition(true));
        setReenterTransition(createXSharedTransition(false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
//        ((MainActivity) getActivity()).getBinding().toolbarTitle.setText("MALL");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMallBetaBinding.inflate(inflater, container, false);


        promotionViewModel = new ViewModelProvider(requireActivity()).get(PromotionViewModel.class);
        storeDirectoryViewModel = new ViewModelProvider(requireActivity()).get(StoreDirectoryViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        initFeaturedAdapter();
        setFeaturedData();

        setupViewPager();


        if(user != null && user.getName() != null){
            binding.layoutHeader.tvHi1.setText("Hi, " + user.getName());
        }
    }

    private void setFeaturedData() {
//        listPromotion = FeaturedRepository.getInstance().generateItems(1);
//
//        adapter.setData(listPromotion);


//        BackgroundHelper.backgroundTask(progress, () -> {
//            listPromotion = FeaturedRepository.getInstance().generateItems(1);
//            return listPromotion;
//        }, new CallbackOnResult<List<Featured>>() {
//            @Override
//            public void onFinish(List<Featured> result) {
//
//                adapter.setData(result);
//            }
//
//            @Override
//            public void onFailed() {
//
//            }
//        });
    }

    private void initFeaturedAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        adapter = new FeaturedCompactAdapter(requireContext(), selectedObject -> {
            Helper.setItemParam(Constants.SELECTED_ITEM, selectedObject);
//            promotionViewModel.setSelectedItem(selectedObject);
            Intent intent = new Intent(requireActivity(), DetailActivity.class);
            startActivity(intent);
        });
        binding.layoutHeader.rvFeatured.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.layoutHeader.rvFeatured.setAdapter(adapter);

        RecyclerView rv = (RecyclerView) binding.layoutHeader.rvFeatured.getChildAt(0);
        rv.setPadding(0, 0, 120, 0);
        rv.setClipToPadding(false);

        /*binding.layoutHeader.rvFeatured.setOffscreenPageLimit(2);

        float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        binding.layoutHeader.rvFeatured.setPageTransformer((page, position) -> {
            float myOffset = position * -(2 * pageOffset + pageMargin);
            if (binding.layoutHeader.rvFeatured.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(binding.layoutHeader.rvFeatured) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.setTranslationX(-myOffset);
                } else {
                    page.setTranslationX(myOffset);
                }
            } else {
                page.setTranslationY(myOffset);
            }
        });*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void showToast(String text) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager() {
        ArrayList<BaseFeatured> listBaseCustomer = new ArrayList<>();
        BaseFeatured base = new BaseFeatured();
        base.setTitle("Promotion");
        listBaseCustomer.add(base);

        BaseFeatured base2 = new BaseFeatured();
        base2.setTitle("Events");
        listBaseCustomer.add(base2);

        BaseFeatured base3 = new BaseFeatured();
        base3.setTitle("Store Directory");
        listBaseCustomer.add(base3);

        PagerAdapter pagerAdapter = new PagerAdapter(this);
        binding.viewPager.setAdapter(pagerAdapter);


        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(listBaseCustomer.get(position).getTitle())
        ).attach();

        BackgroundHelper.backgroundTask(progress, () -> {
            events = FeaturedRepository.getInstance().generateEvents(session.getMallId().get(Constants.KEY_MALL_ID));

            listPromotion = new ArrayList<>();
            listEvents = new ArrayList<>();
            if (events != null && !events.isEmpty()) {
                for (int i = 0; i < events.size(); i++) {
                    if (events.get(i).getType().equals(Constants.PROMOTION_TYPE_CODE)) {
                        listPrePromotion.add(events.get(i));
                    } else {
                        listPreEvents.add(events.get(i));
                    }
                }

                if(listPreEvents != null && !listPreEvents.isEmpty()){
                    for(Event event:listPreEvents){
                        listEvents.add(new Featured(event.getImage(),event.getName(), event.getTitle(), event.getDescription(), event.getLink()));
                    }
                }
                if(listPrePromotion != null && !listPrePromotion.isEmpty()){
                    for(Event promotion:listPrePromotion){
                        listPromotion.add(new Featured(promotion.getImage(),promotion.getName(), promotion.getTitle(), promotion.getDescription(), promotion.getLink()));
                    }
                }
            }

//            listBaseCustomer.get(1).setFeaturedList(FeaturedRepository.getInstance().generateListEvents());
            listBaseCustomer.get(1).setFeaturedList(listEvents);
//            listBaseCustomer.get(0).setFeaturedList(FeaturedRepository.getInstance().generateListPromotions());
            listBaseCustomer.get(0).setFeaturedList(listPromotion);
            listBaseCustomer.get(2).setStoreDirectoryList(StoreDirectoryRepository.getInstance().generateListTenant(session.getMallId().get(Constants.KEY_MALL_ID)));

            return listBaseCustomer.get(1).getFeaturedList();
        }, new CallbackOnResult<List<Featured>>() {
            @Override
            public void onFinish(List<Featured> result) {

                adapter.setData(result);
                pagerAdapter.setData(listBaseCustomer);
            }

            @Override
            public void onFailed() {

            }
        });

//        promotionViewModel.getFeaturedList().observe(getViewLifecycleOwner(), list -> {
//            listBaseCustomer.get(0).setFeaturedList(list);
//            pagerAdapter.setData(listBaseCustomer);
//        });
//
//        promotionViewModel.getEventList().observe(getViewLifecycleOwner(), list -> {
//            listBaseCustomer.get(1).setFeaturedList(list);
//            pagerAdapter.setData(listBaseCustomer);
//        });
//
//        storeDirectoryViewModel.getStores().observe(getViewLifecycleOwner(), storeDirectories -> {
//            listBaseCustomer.get(2).setStoreDirectoryList(storeDirectories);
//            pagerAdapter.setData(listBaseCustomer);
//        });

        /*promotionViewModel.getFeaturedList().observe(getViewLifecycleOwner(), visits -> {
            pagerAdapter.setData(listBaseCustomer);
        });
        promotionViewModel.getFeaturedList().observe(getViewLifecycleOwner(), visits -> {
            pagerAdapter.setData(listBaseCustomer);
        });*/
    }

//    private class ConnectionOdoo extends AsyncTask<Void, String, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                data = new ArrayList<>();
//
//                data = oc.search_read(
//                        "looq_mall.mall",//api
//                        0,
//                        1,//ambil berapa
//                        new Object[]
//                                {new Object[]//conditions/parameter
//                                        {new Object[]{"barcode", "=", scan}}
//                                }
//                        ,Constants.ORDER_PRODUCT
//                        ,Constants.FIELDS_PRODUCT);
//                if (data != null) {
//                    JSONArray jsonArray = new JSONArray(data);
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<Product>>() {
//                    }.getType();
//                    productArrayList = new ArrayList<>();
//                    productArrayList = gson.fromJson(String.valueOf(jsonArray), type);
//                }
//                return true;
//            } catch (Exception e) {
//                error = OdooConnect.testConnection(cd.getUrl(), cd.getPort(), cd.getDb(), cd.getUsername(), cd.getPassword());
//                e.printStackTrace();
//                errorMessage = e.getMessage();
//                return false;
//            }
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            if (result) {
//                if(productArrayList != null && productArrayList.size()!=0) {
//                    productSearch = productArrayList.get(0);
//                    showDialogAddToCart(productSearch);
//                }else{
//                    resumeScanner();
//                    Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
//                }
//                progress.dismiss();
//            } else {
//                if (errorMessage != null && !errorMessage.isEmpty()) {
//                    if (error) {
//                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "User session expired", Toast.LENGTH_SHORT).show();
//                        logOut();
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Connection error when get product", Toast.LENGTH_SHORT).show();
//                }
//                resumeScanner();
//                progress.dismiss();
//            }
//        }
//    }
}
