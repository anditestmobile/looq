package sg.carpark.looq.ui.userpoints;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sg.carpark.looq.data.model.BasePoints;
import sg.carpark.looq.databinding.RowViewPageBinding;

/**
 * Created by TED on 04-Aug-20
 */

public class HistoryPagerAdapter extends RecyclerView.Adapter<HistoryPagerAdapter.DataObjectHolder> {
    private UserPointsActivity userPointsActivity;
    private List<BasePoints> mDataSet = new ArrayList<>();


    public HistoryPagerAdapter(UserPointsActivity userPointsActivity) {
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
        final BasePoints data = mDataSet.get(position);


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(ArrayList<BasePoints> mDataSet) {
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

