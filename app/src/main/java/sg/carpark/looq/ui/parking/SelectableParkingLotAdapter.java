package sg.carpark.looq.ui.parking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.data.model.Mall;
import sg.carpark.looq.data.model.ParkingArea;
import sg.carpark.looq.data.model.SelectableParkingLot;
import sg.carpark.looq.databinding.RowViewSelectableParkingLotBinding;

/**
 * Created by TED on 04-Aug-20
 */

public class SelectableParkingLotAdapter extends RecyclerView.Adapter<SelectableParkingLotViewHolder> implements SelectableParkingLotViewHolder.OnItemSelectedListener {
    private List<SelectableParkingLot> mValues;
    SelectableParkingLotViewHolder.OnItemSelectedListener listener;
    private ParkingFragment context;

    public SelectableParkingLotAdapter(ParkingFragment context, List<Mall> items,
                                       SelectableParkingLotViewHolder.OnItemSelectedListener listener) {
        this.listener = listener;
        this.context = context;

        mValues = new ArrayList<>();
        if(items != null && !items.isEmpty()){
            for (int i = 0; i < items.size(); i++) {
                Mall item = items.get(i);

                if(i == 0){
                    mValues.add(new SelectableParkingLot(item, true));
                }else{
                    mValues.add(new SelectableParkingLot(item, false));
                }
            }
        }

    }

    @Override
    public SelectableParkingLotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowViewSelectableParkingLotBinding binding = RowViewSelectableParkingLotBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new SelectableParkingLotViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectableParkingLotViewHolder holder, int position) {
        SelectableParkingLot selectableItem = mValues.get(position);

        holder.binding.tvParkingLotName.setText(selectableItem.getName());

        holder.mItem = selectableItem;
        holder.setChecked(holder.mItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return SelectableParkingLotViewHolder.SINGLE_SELECTION;
    }

    @Override
    public void onItemSelected(SelectableParkingLot item) {
        context.moveCamera(item);

        for (SelectableParkingLot selectableItem : mValues) {
            if (!selectableItem.equals(item) && selectableItem.isSelected()) {
                selectableItem.setSelected(false);
            } else if (selectableItem.equals(item) && item.isSelected()) {
                selectableItem.setSelected(true);
            }
        }
        notifyDataSetChanged();
        listener.onItemSelected(item);
    }

}

