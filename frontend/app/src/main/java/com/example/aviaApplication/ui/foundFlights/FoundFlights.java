package com.example.aviaApplication.ui.foundFlights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaApplication.R;
import com.example.aviaApplication.additions.recyclerView.FlightsRecycleViewAdapter;
import com.example.aviaApplication.api.models.Flight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoundFlights extends Fragment {

    private FlightsRecycleViewAdapter flightsRecycleViewAdapter;
    private TextView emptyList;
    private RecyclerView recyclerView;

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
        flightsRecycleViewAdapter = new FlightsRecycleViewAdapter();
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
