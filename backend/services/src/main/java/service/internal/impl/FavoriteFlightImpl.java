package service.internal.impl;

import avia.models.FavoriteFlightModel;
import avia.models.FlightModel;
import avia.repositories.FavoriteFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.internal.FavoriteFlightsService;
import service.internal.FlightService;
import service.mapper.FavoriteFlightMapper;
import service.models.FavoriteFlight;

import java.util.List;

@Service
public class FavoriteFlightImpl implements FavoriteFlightsService {

    private final FavoriteFlightRepository favoriteFlightRepository;
    private final FavoriteFlightMapper flightMapper;
    private final FlightService flightService;

    @Autowired
    public FavoriteFlightImpl(FavoriteFlightRepository flightRepository, FavoriteFlightMapper flightMapper, FlightService flightService) {
        this.favoriteFlightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.flightService = flightService;
    }

    @Override
    public List<FavoriteFlight> getAllFavorite(String userId) {
        List<FavoriteFlightModel> models = favoriteFlightRepository.findAllByUserId(userId);
        return flightMapper.toListFavoriteFlight(models);
    }

    @Override
    public void addToFavorite(FavoriteFlight flight) {
        FavoriteFlightModel model = flightMapper.toFavoriteFlightModel(flight);
        Integer flightId = flightService.addFlight(flight.getFlight());
        model.getFlightModel().setId(flightId);

        try {
            favoriteFlightRepository.save(model);
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean deleteFromFavorite(Integer favoriteId) {
        FavoriteFlightModel favoriteFlightModel = favoriteFlightRepository.findFirstById(favoriteId);
        if (favoriteFlightModel != null) {
            favoriteFlightRepository.delete(favoriteFlightModel);
            return true;
        }
        return false;
    }

    @Override
    public Integer getLikedInfo(FavoriteFlight flight) {
        Integer flightId = flightService.addFlight(flight.getFlight());
        if (flightId != null) {
            FavoriteFlightModel favoriteFlightModel = favoriteFlightRepository.findFirstByFlightModel_IdAndUserId(flightId, flight.getUserId());
            if (favoriteFlightModel != null) {
                return favoriteFlightModel.getId();
            }
        }
        return null;
    }


}
