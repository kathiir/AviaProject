package com.example.aviaapplication.additions.recyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.ui.cities.FragmentCitiesSearch;

import java.util.ArrayList;
import java.util.List;

public class CitiesRecycleViewAdapter extends RecyclerView.Adapter<CitiesRecycleViewAdapter.CitiesViewHolder> implements Filterable {


    public static class CitiesViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCityTv;

        public CitiesViewHolder(View view) {
            super(view);
            nameCityTv = view.findViewById(R.id.element_of_list_cities_name_tv);
        }
    }

    private List<City> filteredCities = new ArrayList<>();
    private List<City> basList = new ArrayList<>();

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredCities = basList;
                } else {
                    List<City> filteredList = new ArrayList<>();
                    for (City movie : basList) {
                        if (movie.getPlaceName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    filteredCities = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCities;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                differ.submitList((ArrayList<City>) results.values);
            }
        };
    }


    private FragmentCitiesSearch fragmentCitiesSearch;

    public CitiesRecycleViewAdapter(FragmentCitiesSearch fragmentCitiesSearch) {
        this.fragmentCitiesSearch = fragmentCitiesSearch;
    }

    private AsyncListDiffer<City> differ = new AsyncListDiffer<>(this, DIFF_CALLBACK);

    private static final DiffUtil.ItemCallback<City> DIFF_CALLBACK = new DiffUtil.ItemCallback<City>() {
        @Override
        public boolean areItemsTheSame(@NonNull City oldProduct, @NonNull City newProduct) {
            return oldProduct.getPlaceId().equals(newProduct.getPlaceId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull City city, @NonNull City city1) {
            return city.equals(city1);
        }
    };

    @NonNull
    @Override
    public CitiesRecycleViewAdapter.CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_of_list_cities, parent, false);
        return new CitiesRecycleViewAdapter.CitiesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesRecycleViewAdapter.CitiesViewHolder holder, int position) {
        City c = differ.getCurrentList().get(position);
        holder.nameCityTv.setText(c.getPlaceName());
        holder.itemView.setOnClickListener(v -> {

            fragmentCitiesSearch.sendChosenData(c);
        });
    }


    public void submitList(List<City> cityList) {
        basList = cityList;
        differ.submitList(cityList);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

}