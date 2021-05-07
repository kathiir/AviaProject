package com.example.aviaapplication.ui.foundFlights;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import com.example.aviaapplication.api.models.Flight;

import java.util.Date;
import java.util.List;

public class FoundFlightViewModel extends AndroidViewModel {

    private FoundFlightsRepository foundFlightsRepository;

    public FoundFlightViewModel(@NonNull Application application) {
        super(application);
        foundFlightsRepository = FoundFlightsRepository.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Flight> findFlights(Long from, Long to, Long cityFrom, Long cityTo){
        return foundFlightsRepository.findAll(from, to, cityFrom, cityTo);
    }
}
