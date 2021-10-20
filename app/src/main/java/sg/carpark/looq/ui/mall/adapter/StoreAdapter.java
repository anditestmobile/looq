package sg.carpark.looq.ui.mall.adapter;

/**
 * Created by TED on 23-Nov-20
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.data.model.Store;
import sg.carpark.looq.databinding.RowViewStoreBinding;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.DataObjectHolder> implements Filterable {
    private List<Store> mList = new ArrayList<>();
    private List<Store> mListFiltered = new ArrayList<>();

    private Context mContext;

    private OnAdapterListener onAdapterListener;

    public StoreAdapter(Context mContext, OnAdapterListener onAdapterListener) {
        this.mContext = mContext;
        this.onAdapterListener = onAdapterListener;
    }

    public void setData(List<Store> mDataSet) {
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
                    List<Store> filteredList = new ArrayList<>();
                    for (Store row : mList) {
                        if (row.getStoreName().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFiltered = (ArrayList<Store>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowViewStoreBinding binding;
        private OnAdapterListener onAdapterListener;

        DataObjectHolder(RowViewStoreBinding binding, OnAdapterListener onAdapterListener) {
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

        return new DataObjectHolder(RowViewStoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {
        final Store data = mListFiltered.get(position);

        holder.binding.tvBlockNumber.setText(data.getBlockName());
        holder.binding.tvStoreName.setText(data.getStoreName());

    }

    @Override
    public int getItemCount() {
        return mListFiltered.size();
    }


    public interface OnAdapterListener {
        void onAdapterClick(Store selectedObject);
    }
}
