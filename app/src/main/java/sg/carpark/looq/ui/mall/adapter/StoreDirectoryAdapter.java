package sg.carpark.looq.ui.mall.adapter;

/**
 * Created by TED on 23-Nov-20
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.data.model.StoreDirectory;
import sg.carpark.looq.databinding.RowViewBaseStoreBinding;
import sg.carpark.looq.ui.mall.MallFragment;
import sg.carpark.looq.utils.helper.Helper;

public class StoreDirectoryAdapter extends RecyclerView.Adapter<StoreDirectoryAdapter.DataObjectHolder> implements Filterable {
    private List<StoreDirectory> mList = new ArrayList<>();
    private List<StoreDirectory> mListFiltered = new ArrayList<>();

    private MallFragment mContext;

    private OnAdapterListener onAdapterListener;
    private StoreAdapter adapter;

    public StoreDirectoryAdapter(MallFragment mContext, OnAdapterListener onAdapterListener) {
        this.mContext = mContext;
        this.onAdapterListener = onAdapterListener;
    }

    public void setData(List<StoreDirectory> mDataSet) {
        this.mList = mDataSet;
        this.mListFiltered = mDataSet;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFiltered = mList;
                } else {
                    List<StoreDirectory> filteredList = new ArrayList<>();
                    for (StoreDirectory row : mList) {
                        if (row.getFloorName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFiltered = (ArrayList<StoreDirectory>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowViewBaseStoreBinding binding;
        private OnAdapterListener onAdapterListener;

        DataObjectHolder(RowViewBaseStoreBinding binding, OnAdapterListener onAdapterListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onAdapterListener = onAdapterListener;

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAdapterListener.onAdapterClick(mListFiltered.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new DataObjectHolder(RowViewBaseStoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {
        final StoreDirectory data = mListFiltered.get(position);

        try{
            holder.binding.tvFloorName.setText(Helper.getDayNumberSuffix(Integer.parseInt(data.getStoreList().get(0).getBlockName().substring(0,2))) + " Floor");
        }catch (Exception e){

        }
        adapter = new StoreAdapter(mContext.requireContext(), selectedObject -> {

        });

        mContext.initRecycleView(holder.binding.rvStore, adapter, false);
        adapter.setData(data.getStoreList());

    }

    @Override
    public int getItemCount() {
        return mListFiltered.size();
    }


    public interface OnAdapterListener {
        void onAdapterClick(StoreDirectory selectedObject);
    }
}
