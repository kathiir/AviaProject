package com.example.aviaapplication.ui.flightHistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.additions.recyclerView.FlightHistoryRecyclerViewAdapter;
import com.example.aviaapplication.api.models.Flight;

import java.util.List;

public class FlightHistoryFragment extends Fragment {
    public ProgressBar progressBar;
    private FlightHistoryViewModel flightHistoryViewModel;
    private RecyclerView recyclerView;
    private FlightHistoryRecyclerViewAdapter flightHistoryRecyclerViewAdapter;
    private LinearLayout emptyHistoryLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_flights, container, false);
        flightHistoryViewModel = new ViewModelProvider(this).get(FlightHistoryViewModel.class);
        initViews(view);
        setUpListeners();
        return view;
    }

    private void initViews(View view){
        emptyHistoryLayout = view.findViewById(R.id.flights_history_ll);
        progressBar = view.findViewById(R.id.flight_history_pb);
        flightHistoryRecyclerViewAdapter =  new FlightHistoryRecyclerViewAdapter();
        recyclerView = view.findViewById(R.id.flight_history_rv);
        recyclerView.setAdapter(flightHistoryRecyclerViewAdapter);

        flightHistoryViewModel.getFlights().observe(getViewLifecycleOwner(), this::updateList);
    }

    public void updateList(List<Flight> list){
        if (list.isEmpty()){
            emptyHistoryLayout.setVisibility(View.VISIBLE);
        }else {
            emptyHistoryLayout.setVisibility(View.GONE);
            flightHistoryRecyclerViewAdapter.submitList(list);
        }
    }

    private void setUpListeners(){
        requireActivity().getOnBackPressedDispatcher().addCallback(callback);
    }

    OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            getParentFragmentManager().popBackStack();
        }
    };


}
