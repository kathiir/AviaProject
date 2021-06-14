package service.internal;

import service.models.FavoriteFlight;
import service.models.Flight;
import service.models.RecentFlight;

import java.io.IOException;
import java.util.List;

public interface FlightService {

    List<RecentFlight> getRecentFlights(String userId);

    List<Flight> searchFlight(Flight flight) throws IOException;

    Integer addFlight(Flight flight);

    Integer addToRecent(RecentFlight recentFlight);

    void updateCosts() throws IOException;

}
