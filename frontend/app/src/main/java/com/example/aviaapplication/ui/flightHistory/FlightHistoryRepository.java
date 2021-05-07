package com.example.aviaapplication.ui.flightHistory;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.api.models.RecentFlight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightHistoryRepository {
    private static FlightHistoryRepository flightHistoryRepository;
    private Api api;

    public static FlightHistoryRepository getInstance(){
        if (flightHistoryRepository == null)
            flightHistoryRepository = new FlightHistoryRepository(Api.getInstance());
        return flightHistoryRepository;
    }

    private FlightHistoryRepository(Api api){
        this.api = api;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<RecentFlight> getFlights(){
        return api.getRecentFlights();
//        return Arrays.asList(new Flight("123", "456", 100f, "2222222"), new Flight("456", "123", 200f, "1000"));
    }
}
