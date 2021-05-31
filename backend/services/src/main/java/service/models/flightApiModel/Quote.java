package service.models.flightApiModel;

import lombok.Data;

import java.util.Date;

@Data
public class Quote {
    Integer QuoteId;
    Double MinPrice;
    OutboundDate OutboundLeg;
    Date QuoteDateTime;
}
