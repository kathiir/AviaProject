package com.example.aviaapplication.ui.favoriteFlights;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.Flight;

import java.util.List;

public class FavoriteFlightsRepository {

    private static FavoriteFlightsRepository favoriteFlightsRepository;
    private Api api;

    public static FavoriteFlightsRepository getInstance(){
        if (favoriteFlightsRepository == null)
            favoriteFlightsRepository = new FavoriteFlightsRepository();

        return favoriteFlightsRepository;
    }

    private FavoriteFlightsRepository(){
        api = Api.getInstance();
    }

    public List<Flight> getFavoriteFlights(){
        return api.getFavoriteFlights(1L);
    }
}
