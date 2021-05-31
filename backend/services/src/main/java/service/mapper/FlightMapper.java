package service.mapper;

import avia.models.FlightModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import service.models.Flight;
import service.models.flightApiModel.Quote;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CityMapper.class})
public interface FlightMapper {

    @Mapping(target = "cost", source = "minPrice")
    @Mapping(target = "inboundDate", source = "outboundLeg.landingDate")
    @Mapping(target = "outboundDate", source = "outboundLeg.departureDate")
    @Mapping(target = "originPlace", source = "outboundLeg.originPlace")
    @Mapping(target = "destinationPlace", source = "outboundLeg.destinationPlace")
    Flight toFlight(Quote quote);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "originPlace", source = "originPlace")
    @Mapping(target = "destinationPlace", source = "destinationPlace")
    @Mapping(target = "outboundDate", source = "outboundDate")
    @Mapping(target = "inboundDate", source = "inboundDate")
    @Mapping(target = "cost", source = "cost")
    FlightModel toFlightModel(Flight flight);

    default List<Flight> toListPurchase(List<Quote> list) {
        return list
                .stream()
                .map(this::toFlight)
                .collect(Collectors.toList());
    }

}
