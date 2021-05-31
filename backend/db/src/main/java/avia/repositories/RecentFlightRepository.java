package avia.repositories;

import avia.models.RecentFlightModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecentFlightRepository extends CrudRepository<RecentFlightModel, Integer> {
    List<RecentFlightModel> findAllByUserId(String userId);
}
