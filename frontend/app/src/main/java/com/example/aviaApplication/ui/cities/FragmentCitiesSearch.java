package com.example.aviaApplication.ui.cities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaApplication.R;
import com.example.aviaApplication.additions.recyclerView.CitiesRecycleViewAdapter;
import com.example.aviaApplication.api.models.City;
import com.example.aviaApplication.ui.searchFlights.SearchFlightsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentCitiesSearch extends Fragment {
    private RecyclerView recyclerViewRecentCities;
    private RecyclerView recyclerViewAllCities;
    private CitiesRecycleViewAdapter recycleViewAdapterRecentCities;
    private CitiesRecycleViewAdapter recycleViewAdapterAllCities;
    Integer containerId;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        initViews(view);
        setListeners();
        containerId = container.getId();
        return view;
    }

    public void sendChosenData(City city) {
        SearchFlightsFragment f = (SearchFlightsFragment) getTargetFragment();
        if (getTag().equals("from")) {
            f.setCityFrom(city);
        } else {
            f.setCityTo(city);
        }
        getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(SearchFlightsFragment.class.toString())
                .commit();
    }

    private void setListeners() {
    }

    private void initViews(View view) {
        recycleViewAdapterRecentCities = new CitiesRecycleViewAdapter(this);
        recycleViewAdapterAllCities = new CitiesRecycleViewAdapter(this);
        recyclerViewRecentCities = view.findViewById(R.id.fragment_cities_recent_cities_rv);
        recyclerViewAllCities = view.findViewById(R.id.fragment_cities_all_cities_rv);
        recyclerViewRecentCities.setAdapter(recycleViewAdapterRecentCities);
        recyclerViewAllCities.setAdapter(recycleViewAdapterAllCities);
        updateList(new ArrayList<>());
    }

    public void getRecentCites() {
        //метод из ViewModel
    }

    public void updateList(List<City> list) {
        list.addAll(Arrays.asList(new City(), new City(), new City(), new City(), new City(), new City(), new City(), new City()));
        recycleViewAdapterRecentCities.submitList(list);
        recycleViewAdapterAllCities.submitList(Arrays.asList(new City(), new City(), new City()));

    }
}
