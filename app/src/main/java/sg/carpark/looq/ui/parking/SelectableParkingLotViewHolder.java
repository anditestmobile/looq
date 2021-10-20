package sg.carpark.looq.ui.parking;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import sg.carpark.looq.data.model.SelectableParkingLot;
import sg.carpark.looq.databinding.RowViewSelectableParkingLotBinding;

/**
 * Created by TED on 22-Nov-20
 */
public class SelectableParkingLotViewHolder extends RecyclerView.ViewHolder {

    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    public RowViewSelectableParkingLotBinding binding;
    public SelectableParkingLot mItem;
    public OnItemSelectedListener itemSelectedListener;


    public SelectableParkingLotViewHolder(RowViewSelectableParkingLotBinding binding, OnItemSelectedListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.itemSelectedListener = listener;

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(true);
                itemSelectedListener.onItemSelected(mItem);
            }
        });
    }

    public void setChecked(boolean value) {
        if (value) {
            binding.getRoot().setChecked(true);
        } else {
            binding.getRoot().setChecked(false);
        }
        mItem.setSelected(value);
    }

    public interface OnItemSelectedListener {

        void onItemSelected(SelectableParkingLot item);
    }

}