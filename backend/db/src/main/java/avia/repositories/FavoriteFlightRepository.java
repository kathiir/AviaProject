package avia.repositories;

import avia.models.FavoriteFlightModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface FavoriteFlightRepository extends CrudRepository<FavoriteFlightModel, Integer> {

    List<FavoriteFlightModel> findAllByUserId(String userId);
    FavoriteFlightModel findFirstById(Integer id);
    FavoriteFlightModel findFirstByFlightModel_IdAndUserId(Integer flightId, String userId );
}
