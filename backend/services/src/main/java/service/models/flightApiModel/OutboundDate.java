package service.models.flightApiModel;

import lombok.Data;

import java.util.Date;

@Data
public class OutboundDate {
    Integer OriginId;
    Integer DestinationId;
    Place originPlace;
    Place destinationPlace;
    Date DepartureDate;
    Date landingDate;
}
