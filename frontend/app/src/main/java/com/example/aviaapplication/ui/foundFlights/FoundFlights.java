package com.example.aviaapplication.ui.foundFlights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.additions.recyclerView.FlightsRecycleViewAdapter;
import com.example.aviaapplication.api.models.Flight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoundFlights extends Fragment {

    private FlightsRecycleViewAdapter flightsRecycleViewAdapter;
    private TextView emptyList;
    private RecyclerView recyclerView;
public int containerId ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_found_flights, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.found_flights_rv);
        emptyList = view.findViewById(R.id.found_flights_tv);
        flightsRecycleViewAdapter = new FlightsRecycleViewAdapter(this);
        recyclerView.setAdapter(flightsRecycleViewAdapter);
        updateList(new ArrayList<>());
    }

    public void updateList(List<Flight> list) {
        list.addAll(Arrays.asList(new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight()));
        if (list.isEmpty()) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
            flightsRecycleViewAdapter.submitList(list);
        }
    }
}
