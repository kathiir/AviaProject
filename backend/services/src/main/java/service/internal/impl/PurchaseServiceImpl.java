package service.internal.impl;

import avia.models.PurchaseModel;
import avia.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.internal.CityService;
import service.internal.FlightService;
import service.internal.PurchaseService;
import service.mapper.PurchaseMapper;
import service.models.Purchase;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper purchaseMapper;
    private final PurchaseRepository purchaseRepository;
    private final FlightService flightService;

    @Autowired
    public PurchaseServiceImpl(PurchaseMapper purchaseMapper, PurchaseRepository purchaseRepository, FlightService flightService) {
        this.purchaseMapper = purchaseMapper;
        this.purchaseRepository = purchaseRepository;
        this.flightService = flightService;
    }

    @Override
    public void takePurchase(Purchase purchase) {
        PurchaseModel model = purchaseMapper.toPurchaseModel(purchase);
        Integer flightId = flightService.addFlight(purchase.getFlight());
        model.getFlightModel().setId(flightId);
        purchaseRepository.save(model);
    }

    @Override
    public List<Purchase> getPurchases(String userId) {
        List<PurchaseModel> models = purchaseRepository.findAllByUserId(userId);
        return purchaseMapper.toListPurchase(models);
    }
}
