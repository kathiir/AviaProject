package service.internal;

import service.models.FavoriteFlight;

import java.util.List;

public interface FavoriteFlightsService {

    List<FavoriteFlight> getAllFavorite(String userId);

    void addToFavorite(FavoriteFlight flight);

    boolean deleteFromFavorite(Integer flightId);

    Integer getLikedInfo(FavoriteFlight flight);
}
