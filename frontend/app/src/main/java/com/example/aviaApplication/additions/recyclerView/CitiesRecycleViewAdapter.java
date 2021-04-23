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
import com.example.aviaApplication.api.models.City;

import java.util.List;

public class CitiesRecycleViewAdapter extends RecyclerView.Adapter<CitiesRecycleViewAdapter.CitiesViewHolder> {
    public static class CitiesViewHolder extends RecyclerView.ViewHolder {

        public CitiesViewHolder(View view) {
            super(view);
        }
    }

    @NonNull
    @Override
    public CitiesRecycleViewAdapter.CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_list_cities, parent, false);
        return new CitiesRecycleViewAdapter.CitiesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesRecycleViewAdapter.CitiesViewHolder holder, int position) {

    }

    private AsyncListDiffer<City> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<City> DIFF_CALLBACK = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldProduct, @NonNull City newProduct) {
            return oldProduct.getId().equals(newProduct.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull City city, @NonNull City city1) {
            return city.equals(city1);
        }
    };

    public void submitList(List<City> cityList) {

        differ.submitList(cityList);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

}