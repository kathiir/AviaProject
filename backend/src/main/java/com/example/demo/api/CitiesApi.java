package com.example.demo.api;

import org.springframework.web.bind.annotation.*;
import service.facade.CityFacade;
import service.internal.CityService;
import service.models.RecentCity;
import service.models.city.City;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(
        value = "/city",
        produces = "application/json"
)
public class CitiesApi {

    private final CityFacade cityFacade;

    @Autowired
    public CitiesApi( CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }

    @GetMapping(value = "/user/{user_id}")
    List<RecentCity> getRecentCities(@PathVariable("user_id") String userId) {
        return cityFacade.getRecentCities(userId);
    }

    @PostMapping(value = "/user")
    void addRecentCity(@RequestBody RecentCity city)  {
        cityFacade.addRecentCity(city);
    }

    @GetMapping(value = "/{name}")
    List<City> findCity(@PathVariable("name") String name) throws IOException {
        return cityFacade.searchPlaceByName(name);
    }

}
