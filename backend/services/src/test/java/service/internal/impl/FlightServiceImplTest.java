package service.internal.impl;

import config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import service.internal.FlightService;
import service.models.Flight;
import service.models.RecentFlight;
import service.models.city.City;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Transactional
@SpringBootTest(classes = TestConfig.class)
public class FlightServiceImplTest {
    @Autowired
    private FlightService flightService;
    private List<Flight> flightList = Arrays.asList(new Flight(), new Flight(), new Flight());
    private List<RecentFlight> recentFlightList = Arrays.asList(new RecentFlight(), new RecentFlight(), new RecentFlight());

    @BeforeEach
    void setUp() {
        for (int i = 0; i < flightList.size(); i++) {
            flightList.set(i, setFlightInfo());
        }
        for (int i = 0; i < recentFlightList.size(); i++) {
            recentFlightList.set(i, setRecentFlightInfo());
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

    private RecentFlight setRecentFlightInfo() {
        RecentFlight recentFlight = new RecentFlight();

        recentFlight.setUserId("TestUser");

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
        recentFlight.setFlight(flight);
        return recentFlight;
    }

    @Test
    public void testGetRecentFlights() {
        for (int i = 0; i < recentFlightList.size(); i++) {
            Integer id = flightService.addToRecent(recentFlightList.get(i));
            Assertions.assertNotNull(id);
        }
        Assertions.assertEquals(3, flightService.getRecentFlights("TestUser").size());

    }

    @Test
    public void testAddToRecent() {
        for (int i = 0; i < recentFlightList.size(); i++) {
            Integer id = flightService.addToRecent(recentFlightList.get(i));
            Assertions.assertNotNull(id);
        }
    }

    @Test
    public void testAddFlight() {
        for (int i = 0; i < flightList.size(); i++) {
            Integer id = flightService.addFlight(flightList.get(i));
            flightList.get(i).setId(id);
            flightList.set(i, flightList.get(i));
            Assertions.assertNotNull(id);
        }
    }
}