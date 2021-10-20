package sg.carpark.looq.ui.detail;

/**
 * Created by TED on 23-Nov-20
 */

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.data.model.Detail;
import sg.carpark.looq.databinding.RowViewParagraphBinding;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DataObjectHolder> implements Filterable {
    private List<Detail> mList = new ArrayList<>();
    private List<Detail> mListFiltered = new ArrayList<>();

    private Context mContext;


    public DetailAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Detail> mDataSet) {
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
                    List<Detail> filteredList = new ArrayList<>();
                    for (Detail row : mList) {
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
                mListFiltered = (ArrayList<Detail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class DataObjectHolder extends RecyclerView.ViewHolder{
        private RowViewParagraphBinding binding;

        DataObjectHolder(RowViewParagraphBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new DataObjectHolder(RowViewParagraphBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {
        final Detail data = mListFiltered.get(position);
        holder.binding.tvParagraph.setText(Html.fromHtml(data.getTitle()));
    }

    @Override
    public int getItemCount() {
        return mListFiltered.size();
    }
}
