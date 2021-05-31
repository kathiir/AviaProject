package avia.repositories;

import avia.models.RecentCityModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecentCityRepository extends CrudRepository<RecentCityModel, Integer> {
    List<RecentCityModel> findAllByUserId(String userId);
}
