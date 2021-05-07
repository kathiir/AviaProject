package com.example.aviaapplication.ui.searchFlights;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.aviaapplication.api.models.Flight;

import java.util.List;

public class SearchFlightViewModel extends AndroidViewModel {

    private SearchFlightsRepository searchFlightsRepository;

    public SearchFlightViewModel(@NonNull Application application) {
        super(application);
        searchFlightsRepository = SearchFlightsRepository.getInstance();
    }

    public List<Flight> getRecentFlights(){
        return searchFlightsRepository.getViewedFlightList();
    }

}
