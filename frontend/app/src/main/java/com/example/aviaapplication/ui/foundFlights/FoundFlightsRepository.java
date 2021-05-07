package com.example.aviaapplication.ui.foundFlights;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.Flight;

import java.util.Date;
import java.util.List;

public class FoundFlightsRepository {

    public static FoundFlightsRepository foundFlightsRepository;
    private Api api;


    public static FoundFlightsRepository getInstance(){
        if (foundFlightsRepository == null)
            foundFlightsRepository = new FoundFlightsRepository();

        return foundFlightsRepository;
    }

    public FoundFlightsRepository(){
        api = Api.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Flight> findAll(Long from, Long to, Long fromCity, Long toCity){
        List<Flight> aaa = api.find(from, to, fromCity, toCity);
        return api.find(from, to, fromCity, toCity);
    }
}
