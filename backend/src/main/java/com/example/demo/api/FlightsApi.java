package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.facade.FlightFacade;
import service.models.Flight;
import service.models.RecentFlight;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(
        value = "/flight",
        produces = "application/json"
)
public class FlightsApi {


    private final FlightFacade flightFacade;

    @Autowired
    public FlightsApi(FlightFacade flightFacade) {
        this.flightFacade = flightFacade;
    }

    @GetMapping(value = "/{userId}")
    public List<RecentFlight> getRecentFlights(@PathVariable String userId) {
        return flightFacade.getRecentFlights(userId);
    }

    @PostMapping(value = "/search", consumes = "application/json")
    public List<Flight> searchFlight(@RequestBody Flight flight) throws IOException {
        return flightFacade.searchFlight(flight);
    }

    @PostMapping(consumes = "application/json")
    public void addToRecent(@RequestBody RecentFlight recentFlight) {
        flightFacade.addToRecent(recentFlight);
    }
}
