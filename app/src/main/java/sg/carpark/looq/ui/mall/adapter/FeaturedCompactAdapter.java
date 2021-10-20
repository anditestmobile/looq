package sg.carpark.looq.ui.mall.adapter;

/**
 * Created by TED on 23-Nov-20
 */

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.R;
import sg.carpark.looq.data.model.Featured;
import sg.carpark.looq.databinding.RowViewFeaturedCompactBinding;
import sg.carpark.looq.utils.helper.Helper;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FeaturedCompactAdapter extends RecyclerView.Adapter<FeaturedCompactAdapter.DataObjectHolder> implements Filterable {
    private List<Featured> mList = new ArrayList<>();
    private List<Featured> mListFiltered = new ArrayList<>();

    private Context mContext;

    private OnAdapterListener onAdapterListener;

    public FeaturedCompactAdapter(Context mContext, OnAdapterListener onAdapterListener) {
        this.mContext = mContext;
        this.onAdapterListener = onAdapterListener;
    }

    public void setData(List<Featured> mDataSet) {
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
                    List<Featured> filteredList = new ArrayList<>();
                    for (Featured row : mList) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFiltered = (ArrayList<Featured>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowViewFeaturedCompactBinding binding;
        private OnAdapterListener onAdapterListener;

        DataObjectHolder(RowViewFeaturedCompactBinding binding, OnAdapterListener onAdapterListener) {
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

        return new DataObjectHolder(RowViewFeaturedCompactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {
        final Featured data = mListFiltered.get(position);

//        if(position == 0){
//            Glide.with(mContext)
//                    .load(mContext.getResources().getDrawable(R.drawable.promo_zara))
//                    .transition(withCrossFade())
//                    .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
//                    .into(holder.binding.ivFeatured);
//        }else if(position == 1){
//            Glide.with(mContext)
//                    .load(mContext.getResources().getDrawable(R.drawable.promo_yakun))
//                    .transition(withCrossFade())
//                    .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
//                    .into(holder.binding.ivFeatured);
//        }else{
//            Glide.with(mContext)
//                    .load(data.getImgUrl())
//                    .transition(withCrossFade())
//                    .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
//                    .into(holder.binding.ivFeatured);
//        }

        byte[] image = null;
        if(!data.getImgUrl().equals("false")){
            image = Base64.decode(data.getImgUrl(), Base64.DEFAULT);
        }
        Glide.with(mContext)
                .load(image)
                .transition(withCrossFade())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_no_image))
                .into(holder.binding.ivFeatured);

        holder.binding.tvFeaturedTitle.setText(Helper.isNull(data.getTitle(), mContext.getResources().getString(R.string.placeholder_empty_string)));
        holder.binding.tvFeaturedDesc.setText(Helper.isNull(data.getDescription(), mContext.getResources().getString(R.string.placeholder_empty_string)));
    }

    @Override
    public int getItemCount() {
        return mListFiltered.size();
    }


    public interface OnAdapterListener {
        void onAdapterClick(Featured selectedObject);
    }
}
