package avia.repositories;

import avia.models.CityModel;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;
public interface CityRepository extends CrudRepository<CityModel, Integer> {

    CityModel findFirstByPlaceIdAndCountryNameAndCityIdAndPlaceName(String placeId, String countryName, String cityId, String placeName);
}
