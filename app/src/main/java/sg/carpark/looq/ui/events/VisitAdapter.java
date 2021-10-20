package sg.carpark.looq.ui.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sg.carpark.looq.R;
import sg.carpark.looq.data.model.VisitExt;
import sg.carpark.looq.databinding.RowViewCustomerBinding;
import sg.carpark.looq.utils.helper.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TED on 04-Aug-20
 */

public class VisitAdapter extends RecyclerView.Adapter<VisitAdapter.DataObjectHolder> implements Filterable {
    private List<VisitExt> customerList = new ArrayList<>();
    private List<VisitExt> customerListFiltered = new ArrayList<>();
    private Context mContext;

    private boolean isCalendar;

    public VisitAdapter(Context context, List<VisitExt> data) {
        customerListFiltered = data;
        customerList = data;
        mContext = context;
    }


    public void setData(List<VisitExt> mDataSet) {
        this.customerList = mDataSet;
        this.customerListFiltered = mDataSet;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customerListFiltered = customerList;
                } else {
                    List<VisitExt> filteredList = new ArrayList<>();
                    for (VisitExt row : customerList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCustomerName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    customerListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = customerListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                customerListFiltered = (ArrayList<VisitExt>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        private RowViewCustomerBinding binding;

        DataObjectHolder(RowViewCustomerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new DataObjectHolder(RowViewCustomerBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {
        final VisitExt data = customerList.get(position);

        if(data.getVisit().getId_customer() != null){
            holder.binding.txtCustomerCode.setText(data.getVisit().getId_customer().trim());
        }else{
            holder.binding.txtCustomerCode.setVisibility(View.GONE);
        }

        holder.binding.txtCustomerName.setText(Helper.isEmpty(data.getVisit().getCustomer_name(), mContext.getString(R.string.placeholder_empty_string)));
        holder.binding.tvCustomerAddress.setText(Helper.isEmpty(data.getVisit().getAddress_1(), mContext.getString(R.string.placeholder_empty_string)));

    }

    @Override
    public int getItemCount() {
        return customerListFiltered != null ? customerListFiltered.size() : 0;
    }


    public interface OnImgDeleteListener{
        void onImgDeleteClick(int position);
    }
}

