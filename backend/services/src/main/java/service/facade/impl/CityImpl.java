package service.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import service.facade.CityFacade;
import service.internal.CityService;
import service.models.RecentCity;
import service.models.city.City;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Service
public class CityImpl implements CityFacade {
    private final CityService cityService;

    public CityImpl(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public List<RecentCity> getRecentCities(String userId) {
        return cityService.getRecentCities(userId);
    }

    @Override
    public List<City> searchPlaceByName(String name) throws IOException {
        return cityService.searchPlaceByName(name);
    }

    @Override
    public void addRecentCity(@Valid RecentCity recentCity) {
        cityService.addRecentCity(recentCity);
    }
}
