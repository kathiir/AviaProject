package com.example.aviaApplication.ui.flightInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaApplication.R;
import com.example.aviaApplication.additions.recyclerView.PassengerListRecyclerViewAdapter;
import com.example.aviaApplication.api.models.Flight;
import com.example.aviaApplication.api.models.Passenger;
import com.example.aviaApplication.utils.FragmentChangingUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PassengerListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PassengerListRecyclerViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flight_passengers, container, false);
        initViews(root);
        setUpListeners();
        return root;
    }

    private void initViews(View root){
        adapter = new PassengerListRecyclerViewAdapter();
        recyclerView = root.findViewById(R.id.flight_passengers_rv);
        recyclerView.setAdapter(adapter);

        updateList(new ArrayList<>());
    }

    private void setUpListeners(){
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(FragmentChangingUtils.getOnBackPressedCallback(getParentFragmentManager()));
    }

    public void updateList(List<Passenger> passengerList){
        passengerList.addAll(Arrays.asList(new Passenger(), new Passenger(),new Passenger(),new Passenger()));
        adapter.submitList(passengerList);
    }


}
