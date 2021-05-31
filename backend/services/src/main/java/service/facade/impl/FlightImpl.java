package service.facade.impl;

import org.springframework.stereotype.Service;
import service.facade.FlightFacade;
import service.internal.FlightService;
import service.models.Flight;
import service.models.RecentFlight;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Service
public class FlightImpl implements FlightFacade {
    private final FlightService flightService;

    public FlightImpl(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public List<RecentFlight> getRecentFlights(String userId) {
        return flightService.getRecentFlights(userId);
    }

    @Override
    public List<Flight> searchFlight(Flight flight) throws IOException {
        return flightService.searchFlight(flight);
    }

    @Override
    public void addToRecent(@Valid RecentFlight recentFlight) {
        flightService.addToRecent(recentFlight);
    }
}
