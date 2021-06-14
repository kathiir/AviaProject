package service.internal.impl;

import config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import service.internal.FavoriteFlightsService;
import service.models.FavoriteFlight;
import service.models.Flight;
import service.models.city.City;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Transactional
@SpringBootTest(classes = TestConfig.class)
public class FavoriteFlightImplTest {
    @Autowired
    private FavoriteFlightsService favoriteFlightsService;
    private List<Flight> flightList = Arrays.asList(new Flight(), new Flight(), new Flight());
    private List<FavoriteFlight> favoriteFlights = Arrays.asList(new FavoriteFlight(), new FavoriteFlight(), new FavoriteFlight());

    @BeforeEach
    void setUp() {
        for (int i = 0; i < flightList.size(); i++) {
            flightList.set(i, setFlightInfo());
        }
        for (int i = 0; i < favoriteFlights.size(); i++) {
            favoriteFlights.set(i, setFavoriteFlightInfo());
        }
    }

    private Flight setFlightInfo() {

        Flight flight = new Flight();
        flight.setCost(21390.0);
        flight.setInboundDate(new Date(System.currentTimeMillis()));
        flight.setOutboundDate(new Date(System.currentTimeMillis()));
        City city = new City();
        city.setCityId("-sky");
        city.setCountryName("Молдавия");
        city.setPlaceId("MD-sky");
        city.setPlaceName("Молдавия");
        City city2 = new City();
        city2.setCityId("MVFA-sky");
        city2.setCountryName("Бразилия");
        city2.setPlaceId("MVF-sky");
        city2.setPlaceName("Моссоро");
        flight.setDestinationPlace(city);
        flight.setOriginPlace(city2);
        return flight;
    }

    private FavoriteFlight setFavoriteFlightInfo() {
        FavoriteFlight favoriteFlight = new FavoriteFlight();

        favoriteFlight.setUserId("TestUser");

        Flight flight = new Flight();
        flight.setCost(21390.0);
        flight.setInboundDate(new Date(ThreadLocalRandom.current().nextInt() * 1000L));
        flight.setOutboundDate(new Date(ThreadLocalRandom.current().nextInt() * 1000L));
        City city = new City();
        city.setCityId("-sky");
        city.setCountryName("Молдавия");
        city.setPlaceId("MD-sky");
        city.setPlaceName("Молдавия");
        City city2 = new City();
        city2.setCityId("MVFA-sky");
        city2.setCountryName("Бразилия");
        city2.setPlaceId("MVF-sky");
        city2.setPlaceName("Моссоро");
        flight.setDestinationPlace(city);
        flight.setOriginPlace(city2);
        favoriteFlight.setFlight(flight);
        return favoriteFlight;
    }

    @Test
    public void testGetAllFavorite() {

        Integer id = favoriteFlightsService.addToFavorite(favoriteFlights.get(0));
        Assertions.assertNotNull(id);
        Assertions.assertEquals(1, favoriteFlightsService.getAllFavorite("TestUser").size());
    }

    @Test
    public void testAddToFavorite() {
        for (int i = 0; i < favoriteFlights.size(); i++) {
            Integer id = favoriteFlightsService.addToFavorite(favoriteFlights.get(i));
            Assertions.assertNotNull(id);
        }
    }

    @Test
    public void testDeleteFromFavorite() {
        Integer id = favoriteFlightsService.addToFavorite(favoriteFlights.get(0));
        Assertions.assertNotNull(id);

        Assertions.assertTrue(favoriteFlightsService.deleteFromFavorite(id));
        Assertions.assertFalse(favoriteFlightsService.deleteFromFavorite(id));

    }

    @Test
    public void testGetLikedInfo() {
        Integer id = favoriteFlightsService.addToFavorite(favoriteFlights.get(0));
        Assertions.assertEquals(id, favoriteFlightsService.getLikedInfo(favoriteFlights.get(0)));
    }
}