package com.example.aviaapplication.ui.favoriteFlights;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.aviaapplication.api.models.Flight;

import java.util.List;

public class FavoriteFlightsViewModel extends AndroidViewModel {
    private FavoriteFlightsRepository favoriteFlightsRepository;


    public FavoriteFlightsViewModel(@NonNull Application application) {
        super(application);
        favoriteFlightsRepository = FavoriteFlightsRepository.getInstance();
    }

    public List<Flight> getFavoriteFlights(){
        return favoriteFlightsRepository.getFavoriteFlights();
    }
    public void getIsLoadingFavoriteFlights(){

    }
}
