package com.example.aviaApplication.ui.favoriteFlights;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaApplication.R;
import com.example.aviaApplication.additions.recyclerView.FavoriteFlightsRecyclerViewAdapter;
import com.example.aviaApplication.api.models.Flight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteFlightsFragment extends Fragment {
    private FavoriteFlightsViewModel favoriteFlightsViewModel;
    private RecyclerView recyclerView;
    public ProgressBar progressBar;
    private Integer idUser;
    private FavoriteFlightsRecyclerViewAdapter mAdapter;
    private LinearLayout emptyFavoriteListLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_flights, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        emptyFavoriteListLL = view.findViewById(R.id.favorite_flights_ll);
        progressBar = view.findViewById(R.id.favorite_flights_pb);
        mAdapter = new FavoriteFlightsRecyclerViewAdapter(this);
        recyclerView = view.findViewById(R.id.favorite_flights_rv);
        recyclerView.setAdapter(mAdapter);
        updateList(new ArrayList<>());
    }

    public void getExhibits() {
        //метод из ViewModel
    }

    public void updateList(List<Flight> list) {
        list.addAll(Arrays.asList(new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight()));
        if (list.isEmpty()) {
            emptyFavoriteListLL.setVisibility(View.VISIBLE);
        } else {
            emptyFavoriteListLL.setVisibility(View.GONE);
            mAdapter.submitList(list);
        }
    }
}
