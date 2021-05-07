package com.example.aviaapplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaapplication.ui.foundFlights.FoundFlights;
import com.example.aviaapplication.utils.CommonUtils;

import java.util.List;

public class FlightsRecycleViewAdapter extends RecyclerView.Adapter<FlightsRecycleViewAdapter.FlightsViewHolder> {
    public static class FlightsViewHolder extends RecyclerView.ViewHolder {
        public FlightsViewHolder(View view) {
            super(view);
        }
    }

    private FoundFlights fragment;

    public FlightsRecycleViewAdapter(FoundFlights fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public FlightsRecycleViewAdapter.FlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_found_flights, parent, false);
        return new FlightsRecycleViewAdapter.FlightsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsRecycleViewAdapter.FlightsViewHolder holder, int position) {
        Flight flight = differ.getCurrentList().get(position);
        holder.itemView.setOnClickListener(v -> CommonUtils.goToFragment(fragment.getParentFragmentManager(),
                R.id.nav_host_fragment, FlightInfoFragment.class));


    }

    private AsyncListDiffer<Flight> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<Flight> DIFF_CALLBACK = new DiffUtil.ItemCallback<Flight>() {
        @Override
        public boolean areItemsTheSame(@NonNull Flight oldProduct, @NonNull Flight newProduct) {
            return oldProduct.getFlightId().equals(newProduct.getFlightId());
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
