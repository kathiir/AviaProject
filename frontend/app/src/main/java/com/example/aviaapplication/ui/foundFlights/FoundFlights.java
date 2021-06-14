package com.example.aviaapplication.ui.foundFlights;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.additions.recyclerView.FlightsRecycleViewAdapter;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaapplication.ui.searchFlights.SearchFlightViewModel;
import com.example.aviaapplication.utils.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import kotlin.jvm.internal.PropertyReference0Impl;


public class FoundFlights extends Fragment {

    private FlightsRecycleViewAdapter flightsRecycleViewAdapter;
    private TextView emptyList;
    private RecyclerView recyclerView;

    private SearchFlightViewModel searchFlightViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        searchFlightViewModel = ViewModelProviders.of(requireActivity()).get(SearchFlightViewModel.class);

        View view = inflater.inflate(R.layout.fragment_found_flights, container, false);
        initViews(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.found_flights_rv);
        emptyList = view.findViewById(R.id.found_flights_tv);
        flightsRecycleViewAdapter = new FlightsRecycleViewAdapter(this);
        recyclerView.setAdapter(flightsRecycleViewAdapter);

        //TODO
        searchFlightViewModel.getFlightsLiveData().observe(getViewLifecycleOwner(), this::updateList);
    }

    public void updateList(List<Flight> list) {
            if (list == null || list.isEmpty()) {
                emptyList.setVisibility(View.VISIBLE);
            } else {
                emptyList.setVisibility(View.GONE);
                flightsRecycleViewAdapter.submitList(list);
            }
    }
}
