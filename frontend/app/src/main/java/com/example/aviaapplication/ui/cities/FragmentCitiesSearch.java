package com.example.aviaapplication.ui.cities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.additions.recyclerView.CitiesRecycleViewAdapter;
import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.ui.searchFlights.SearchFlightsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentCitiesSearch extends Fragment {
    private RecyclerView recyclerViewRecentCities;
    private RecyclerView recyclerViewAllCities;
    private CitiesRecycleViewAdapter recycleViewAdapterRecentCities;
    private CitiesRecycleViewAdapter recycleViewAdapterAllCities;
    Integer containerId;
    SearchView simpleSearchView;

    private CitiesViewModel citiesViewModel;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        citiesViewModel = new ViewModelProvider(this).get(CitiesViewModel.class);
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        initViews(view);
        setListeners();
        containerId = container.getId();
        return view;
    }

    public void sendChosenData(City city) {
        SearchFlightsFragment f = (SearchFlightsFragment) getTargetFragment();
        citiesViewModel.addRecentCity(city);

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

        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recycleViewAdapterAllCities.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recycleViewAdapterAllCities.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void initViews(View view) {
        simpleSearchView = view.findViewById(R.id.fragment_cities_search_city_sv);

        recycleViewAdapterRecentCities = new CitiesRecycleViewAdapter(this);
        recycleViewAdapterAllCities = new CitiesRecycleViewAdapter(this);
        recyclerViewRecentCities = view.findViewById(R.id.fragment_cities_recent_cities_rv);
        recyclerViewAllCities = view.findViewById(R.id.fragment_cities_all_cities_rv);
        recyclerViewRecentCities.setAdapter(recycleViewAdapterRecentCities);
        recyclerViewAllCities.setAdapter(recycleViewAdapterAllCities);
        updateList(citiesViewModel.getAllCities());
    }

    public void getRecentCites() {
        //метод из ViewModel
    }

    public void updateList(List<City> list) {
        recycleViewAdapterAllCities.submitList(list);
        recycleViewAdapterRecentCities.submitList(citiesViewModel.getRecentCities());
    }
}
