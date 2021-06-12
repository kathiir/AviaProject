package avia.repositories;

import avia.models.PurchaseModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<PurchaseModel, Integer> {
    List<PurchaseModel> findAllByUserId(String userId);
    Integer countAllByUserId(String userId);
}
