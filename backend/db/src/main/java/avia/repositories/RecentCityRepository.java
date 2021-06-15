package avia.repositories;

import avia.models.RecentCityModel;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface RecentCityRepository extends CrudRepository<RecentCityModel, Integer> {
    List<RecentCityModel> findAllByUserId(String userId);
}
