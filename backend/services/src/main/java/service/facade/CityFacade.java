package service.facade;

import org.springframework.validation.annotation.Validated;
import service.models.RecentCity;
import service.models.city.City;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Validated
public interface CityFacade {
    List<RecentCity> getRecentCities(String userId);

    List<City> searchPlaceByName(String name) throws IOException;

    void addRecentCity(@Valid RecentCity recentCity);
}
