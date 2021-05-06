package com.example.aviaapplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.Flight;

import java.util.List;

public class FlightHistoryRecyclerViewAdapter extends RecyclerView.Adapter<FlightHistoryRecyclerViewAdapter.FlightHistoryViewHolder> {

    public static class FlightHistoryViewHolder extends RecyclerView.ViewHolder{
        public FlightHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public FlightHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_list_hist_flights, parent, false);
        view.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        view.setDividerDrawable(ResourcesCompat.getDrawable(parent.getResources(), R.drawable.divider_linear_layout, null));
        view.setDividerPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, parent.getResources().getDisplayMetrics()));
        return new FlightHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightHistoryViewHolder holder, int position) {
        TextView destinationTV = holder.itemView.findViewById(R.id.from_to_tv);
        TextView dateTV = holder.itemView.findViewById(R.id.date_tv);
        TextView priceTV = holder.itemView.findViewById(R.id.price_tv);

        Flight flight = differ.getCurrentList().get(position);

        destinationTV.setText(flight.getSrc() + "-" + flight.getDest());
        dateTV.setText(flight.getDate());
        priceTV.setText(flight.getPrice().toString());
    }

    private AsyncListDiffer<Flight> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<Flight> DIFF_CALLBACK = new DiffUtil.ItemCallback<Flight>() {
        @Override
        public boolean areItemsTheSame(@NonNull Flight oldProduct, @NonNull Flight newProduct) {
            return oldProduct.getId().equals(newProduct.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Flight oldProduct, @NonNull Flight newProduct) {
            return oldProduct.equals(newProduct);
        }
    };

    public void submitList(List<Flight> products) {
        differ.submitList(products);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

}
