package service.facade;

import org.springframework.validation.annotation.Validated;
import service.models.Flight;
import service.models.RecentFlight;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Validated
public interface FlightFacade {
    List<RecentFlight> getRecentFlights(String userId);

    void addToRecent(@Valid RecentFlight recentFlight);

    List<Flight> searchFlight(Flight flight) throws IOException;

}
