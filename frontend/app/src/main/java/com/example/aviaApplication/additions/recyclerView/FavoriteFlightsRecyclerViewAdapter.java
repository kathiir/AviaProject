package com.example.aviaApplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaApplication.R;
import com.example.aviaApplication.api.models.Flight;
import com.example.aviaApplication.ui.favoriteFlights.FavoriteFlightsFragment;
import com.example.aviaApplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaApplication.ui.foundFlights.FoundFlights;
import com.example.aviaApplication.utils.FragmentChangingUtils;

import java.util.List;

public class FavoriteFlightsRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteFlightsRecyclerViewAdapter.FavoriteFlightsViewHolder> {
    public static class FavoriteFlightsViewHolder extends RecyclerView.ViewHolder {
        public FavoriteFlightsViewHolder(View view) {
            super(view);
        }
    }

    private FavoriteFlightsFragment fragment;

    public FavoriteFlightsRecyclerViewAdapter(FavoriteFlightsFragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public FavoriteFlightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_list_favorite_flights, parent, false);
        return new FavoriteFlightsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFlightsViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> FragmentChangingUtils.goToFragment(fragment.getParentFragmentManager(),
                R.id.nav_host_fragment, FlightInfoFragment.class));

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
