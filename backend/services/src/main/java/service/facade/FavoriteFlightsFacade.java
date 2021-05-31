package service.facade;

import org.springframework.validation.annotation.Validated;
import service.models.FavoriteFlight;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface FavoriteFlightsFacade {
    List<FavoriteFlight> getAllFavorite(String userId);

    void addToFavorite(@Valid FavoriteFlight flight);

    boolean deleteFromFavorite(Integer flightId);

    Integer getLikedInfo(@Valid FavoriteFlight flight);

}
