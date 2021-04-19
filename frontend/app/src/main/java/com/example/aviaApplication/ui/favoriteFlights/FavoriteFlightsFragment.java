package com.example.aviaApplication.ui.favoriteFlights;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.aviaApplication.R;

public class FavoriteFlightsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return
                inflater.inflate(R.layout.fragment_favorite_flights_empty, container, false);

    }

}
