package com.example.aviaapplication.ui.flightInfo;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.Flight;

import java.util.List;

public class FlightInfoRepository {
    private static FlightInfoRepository repository;
    private Api api;


    public static FlightInfoRepository getInstance(){
        if (repository == null)
            repository = new FlightInfoRepository(Api.getInstance());
        return repository;
    }

    private FlightInfoRepository(Api api) {
        this.api = api;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Flight getFlightByid(Long id){
        return api.getFlightById(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isFavorite(Long userId, Long flightId){
        return api.isFavorite(userId, flightId);
    }

    public void makeFavorite(Long userId, Flight flight){
        api.addFavorite(userId, flight);
    }

    public void removeFavorite(Long userId, Flight flight){
        api.removeFavorite(userId, flight);
    }

    public void addToViewed(Long userId, Flight flight){
        api.addToRecentViewed(userId, flight);
    }
}
