package avia.repositories;

import avia.models.FlightModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
public interface FlightRepository extends CrudRepository<FlightModel, Integer> {
    FlightModel findFirstByOriginPlace_IdAndDestinationPlace_IdAndInboundDateAndOutboundDate(
            Integer orId, Integer destId, Date inbound, Date outBound );

}
