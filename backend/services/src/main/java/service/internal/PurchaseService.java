package service.internal;

import service.models.Purchase;

import java.util.List;

public interface PurchaseService {

    Integer takePurchase(Purchase purchase);

    List<Purchase> getPurchases(String userId);

    Integer getPurchasesCount(String userId);
}
