package service.internal.impl;

import config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import service.internal.CityService;
import service.models.RecentCity;
import service.models.city.City;

import java.util.Arrays;
import java.util.List;

@Transactional
@SpringBootTest(classes = TestConfig.class)
public class CityServiceImplTest {
    @Autowired
    private CityService cityService;

    @BeforeAll
    static void setup() {
    }

    private List<City> cities = Arrays.asList(new City(), new City(), new City());
    private List<RecentCity> recentCities = Arrays.asList(new RecentCity(), new RecentCity(), new RecentCity());

    private City setCitiesInfo() {
        City city = new City();
        city.setCityId("-sky");
        city.setCountryName("Молдавия");
        city.setPlaceId("MD-sky");
        city.setPlaceName("Молдавия");
        return city;
    }

    private RecentCity setRecentCitiesInfo() {
        RecentCity recentCity = new RecentCity();
        recentCity.setUserId("TestUser");
        City city = new City();
        city.setCityId("-sky");
        city.setCountryName("Молдавия");
        city.setPlaceId("MD-sky");
        city.setPlaceName("Молдавия");
        recentCity.setCity(city);
        return recentCity;
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < cities.size(); i++) {
            cities.set(i, setCitiesInfo());
        }
        for (int i = 0; i < recentCities.size(); i++) {
            recentCities.set(i, setRecentCitiesInfo());
        }
    }

    @Test
    public void testGetRecentCities() {
        Integer id = cityService.addRecentCity(recentCities.get(0));
        Assertions.assertNotNull(id);
        Assertions.assertEquals(1, cityService.getRecentCities("TestUser").size());

    }

    @Test
    public void testAddRecentCity() {
        for (int i = 0; i < recentCities.size(); i++) {
            Integer id = cityService.addRecentCity(recentCities.get(0));
            Assertions.assertNotNull(id);
        }
    }
}