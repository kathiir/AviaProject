package com.example.aviaapplication.ui.searchFlights;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.Flight;

import java.util.ArrayList;
import java.util.List;

public class SearchFlightsRepository {

    private static SearchFlightsRepository searchFlightsRepository;
    private Api api;

    public static SearchFlightsRepository getInstance(){
        if (searchFlightsRepository == null)
            searchFlightsRepository = new SearchFlightsRepository();
        return searchFlightsRepository;
    }

    private SearchFlightsRepository(){
        api = Api.getInstance();
    }

    public List<Flight> getViewedFlightList(){
        if (api.isLoggedIn())
            return api.getViewedList(1L);

        return new ArrayList<>();
    }
}
