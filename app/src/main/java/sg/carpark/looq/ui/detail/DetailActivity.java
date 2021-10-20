package sg.carpark.looq.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sg.carpark.looq.R;
import sg.carpark.looq.data.model.Detail;
import sg.carpark.looq.data.model.Featured;
import sg.carpark.looq.data.repository.DetailParagraphRepository;
import sg.carpark.looq.databinding.ActivityDetailBinding;
import sg.carpark.looq.ui.base.BaseActivity;
import sg.carpark.looq.ui.mall.viewmodel.PromotionViewModel;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class DetailActivity extends BaseActivity {
    private ActivityDetailBinding binding;
    private DetailAdapter adapter;
    private PromotionViewModel promotionViewModel;
    private Featured selected = new Featured();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try{

            if(Helper.getItemParam(Constants.SELECTED_ITEM) != null){
                selected = (Featured) Helper.getItemParam(Constants.SELECTED_ITEM);
            }
            binding.tvTitle.setText(selected.getTitle());
            if(!selected.getLink().equals("false")) {
                binding.tvLink.setText(selected.getLink());
            }


//            if(selected.getTitle().contains("ZARA")){
//                Glide.with(this)
//                        .load(getResources().getDrawable(R.drawable.promo_zara))
//                        .transition(withCrossFade())
//                        .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
//                        .into(binding.ivDetail);
//            }else if (selected.getTitle().contains("Ya Kun")){
//                Glide.with(this)
//                        .load(getResources().getDrawable(R.drawable.promo_yakun))
//                        .transition(withCrossFade())
//                        .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
//                        .into(binding.ivDetail);
//            }else{
//                Glide.with(this)
//                        .load(selected.getImgUrl())
//                        .transition(withCrossFade())
//                        .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
//                        .into(binding.ivDetail);
//            }
            byte[] image = null;
            if(!selected.getImgUrl().equals("false")){
                image = Base64.decode(selected.getImgUrl(), Base64.DEFAULT);
            }
            Glide.with(this)
                    .load(image)
                    .transition(withCrossFade())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                    .into(binding.ivDetail);
        }catch (Exception e){

        }

        initFeaturedAdapter();
        setFeaturedData();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setFeaturedData() {
        List<Detail> featuredList = DetailParagraphRepository.generateItems(selected);
        adapter.setData(featuredList);
    }

    private void initFeaturedAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvDetail.setLayoutManager(layoutManager);

        adapter = new DetailAdapter(this);
        binding.rvDetail.setAdapter(adapter);
    }

    public void shareApp() {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
//
//        String extraText = selected.getName() + " " + selected.getLink());
//
//        intent.putExtra(Intent.EXTRA_TEXT, extraText);
//        startActivity(Intent.createChooser(intent, getString(R.string.action_share_app)));
    }

}