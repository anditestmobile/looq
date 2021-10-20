package sg.carpark.looq.ui.mall.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sg.carpark.looq.data.model.BaseFeatured;
import sg.carpark.looq.databinding.RowViewPageBinding;
import sg.carpark.looq.ui.detail.DetailActivity;
import sg.carpark.looq.ui.events.EventDetailActivity;
import sg.carpark.looq.ui.mall.MallFragment;
import sg.carpark.looq.ui.mall.viewmodel.PromotionViewModel;
import sg.carpark.looq.ui.userpoints.UserPointsActivity;
import sg.carpark.looq.utils.constants.Constants;
import sg.carpark.looq.utils.helper.Helper;

/**
 * Created by TED on 04-Aug-20
 */

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.DataObjectHolder> {
    private static final String TAG = "VisitPager";
    private ArrayList<BaseFeatured> mDataSet = new ArrayList<>();
    private MallFragment mContext;
    private UserPointsActivity userPointsActivity;
    private String customerOnVisit;
    private FeaturedAdapter adapter;
    private StoreDirectoryAdapter storeDirectoryAdapter;
    private PromotionViewModel promotionViewModel;

    public PagerAdapter(MallFragment mallFragment) {
        mContext = mallFragment;

        promotionViewModel = new ViewModelProvider(mallFragment.requireActivity()).get(PromotionViewModel.class);
    }

    public PagerAdapter(UserPointsActivity userPointsActivity) {
        this.userPointsActivity = userPointsActivity;
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        private RowViewPageBinding binding;

        DataObjectHolder(RowViewPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new DataObjectHolder(RowViewPageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {
        final BaseFeatured data = mDataSet.get(position);


        if(position == getItemCount() - 1){

            storeDirectoryAdapter = new StoreDirectoryAdapter(mContext, selectedObject -> {

            });

            mContext.initRecycleView(holder.binding.rvChild, storeDirectoryAdapter, false);
            storeDirectoryAdapter.setData(data.getStoreDirectoryList());

        }else{
            adapter = new FeaturedAdapter(mContext.requireContext(), selectedObject -> {
                if(position == 1){
                    Helper.setItemParam(Constants.SELECTED_ITEM, selectedObject);
                    Intent intent = new Intent(mContext.getActivity(), EventDetailActivity.class);
                    mContext.startActivity(intent);
                }else{
                    Helper.setItemParam(Constants.SELECTED_ITEM, selectedObject);
                    Intent intent = new Intent(mContext.requireContext(), DetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
            mContext.initRecycleView(holder.binding.rvChild, adapter, 2);
            adapter.setData(data.getFeaturedList());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(ArrayList<BaseFeatured> mDataSet) {
        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    public boolean isRecyclerScrollable(RecyclerView view) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) view.getLayoutManager();
        RecyclerView.Adapter adapter = view.getAdapter();
        if (layoutManager == null || adapter == null) return false;

        return layoutManager.findLastCompletelyVisibleItemPosition() < adapter.getItemCount() - 1;
    }
}

