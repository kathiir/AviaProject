package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.facade.PurchaseFacade;
import service.internal.PurchaseService;
import service.models.Purchase;

import java.util.List;

@RestController
@RequestMapping(
        value = "/purchase",
        produces = "application/json"
)
public class PurchaseApi {
    private final PurchaseFacade purchaseFacade;

    @Autowired
    public PurchaseApi(PurchaseFacade purchaseFacade) {
        this.purchaseFacade = purchaseFacade;
    }

    @PostMapping(consumes = "application/json")
    public void takePurchase(@RequestBody Purchase purchase) {
        purchaseFacade.takePurchase(purchase);
    }

    @GetMapping(value = "/{userId}")
    public List<Purchase> getPurchases(@PathVariable String userId) {
        return purchaseFacade.getPurchases(userId);
    }
}
