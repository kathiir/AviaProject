package service.facade;

import org.springframework.validation.annotation.Validated;
import service.models.Purchase;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface PurchaseFacade {
    void takePurchase(@Valid Purchase purchase);

    List<Purchase> getPurchases(String userId);
}
