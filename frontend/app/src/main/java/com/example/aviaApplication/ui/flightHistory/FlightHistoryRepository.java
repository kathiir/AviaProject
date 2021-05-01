package com.example.aviaApplication.ui.flightHistory;

import com.example.aviaApplication.api.models.Flight;

import java.util.Arrays;
import java.util.List;

public class FlightHistoryRepository {
    private static FlightHistoryRepository flightHistoryRepository;

    public static FlightHistoryRepository getInstance(){
        if (flightHistoryRepository == null)
            flightHistoryRepository = new FlightHistoryRepository();
        return flightHistoryRepository;
    }

    public List<Flight> getFlights(){
        return Arrays.asList(new Flight("123", "456", 100f, "2222222"), new Flight("456", "123", 200f, "1000"));
    }
}
