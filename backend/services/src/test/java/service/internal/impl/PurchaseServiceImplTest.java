package service.internal.impl;


import avia.repositories.PurchaseRepository;
import config.TestConfig;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import service.internal.PurchaseService;
import service.models.Flight;
import service.models.Purchase;
import service.models.city.City;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional
@SpringBootTest(classes = TestConfig.class)
public class PurchaseServiceImplTest {

    @Autowired
    private PurchaseService purchaseService;
    private List<Purchase> purchaseList = Arrays.asList(new Purchase(), new Purchase(), new Purchase());

    private Purchase setPurchaseInfo() {
        Purchase purchase = new Purchase();
        purchase.setUserId("testUser");
        purchase.setCountPassengers(2);
        purchase.setFlightCost(30410.0);
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
        purchase.setFlight(flight);

        return purchase;
    }

    @BeforeEach
    void setUp() {
        for (int i = 0; i < purchaseList.size(); i++) {
            purchaseList.set(i, setPurchaseInfo());
        }
    }

    @Test
    public void testGetPurchasesCount() {
        for (int i = 0; i < purchaseList.size(); i++) {
            purchaseService.takePurchase(purchaseList.get(i));
        }
        Assertions.assertEquals(3, purchaseService.getPurchasesCount("testUser"));
    }


}