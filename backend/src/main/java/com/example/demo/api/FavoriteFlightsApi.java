package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.facade.FavoriteFlightsFacade;
import service.internal.FavoriteFlightsService;
import service.models.FavoriteFlight;

import java.util.List;

@RestController
@RequestMapping(
        value = "/favorite",
        produces = "application/json"
)
public class FavoriteFlightsApi {

    private final FavoriteFlightsFacade favoriteFlightsFacade;

    @Autowired
    public FavoriteFlightsApi(FavoriteFlightsFacade favoriteFlightsFacade) {
        this.favoriteFlightsFacade = favoriteFlightsFacade;
    }

    @GetMapping(value = "/{user_id}")
    List<FavoriteFlight> getFavoriteFlights(@PathVariable("user_id") String userId) {
        return favoriteFlightsFacade.getAllFavorite(userId);
    }

    @PostMapping(value = "/likedInfo", consumes = "application/json")
    Integer getInfo(@RequestBody FavoriteFlight flight) {
        return favoriteFlightsFacade.getLikedInfo(flight);
    }
    
    @PostMapping(consumes = "application/json")
    void addToFavorite(@RequestBody FavoriteFlight flight) {
        favoriteFlightsFacade.addToFavorite(flight);
    }

    @PostMapping(value = "/delete", consumes = "application/json")
    Boolean deleteFromFavorite(@RequestBody FavoriteFlight flight){
        return favoriteFlightsFacade.deleteFromFavorite(flight);
    }

    @DeleteMapping(value = "/{flight_id}")
    boolean deleteFromFavorite(@PathVariable("flight_id") Integer flightId) {
        return favoriteFlightsFacade.deleteFromFavorite(flightId);
    }

}
