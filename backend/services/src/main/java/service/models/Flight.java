package service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.models.city.City;
import service.utils.ValidationErrorTerms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    Integer id;
    @Valid
    City originPlace;

    @Valid
    City destinationPlace;

    @NotNull(message = ValidationErrorTerms.OUTBOUND_DATE_MUST_BE_SET)
    Date outboundDate;

    @NotNull(message = ValidationErrorTerms.INBOUND_DATE_MUST_BE_SET)
    Date inboundDate;

    @NotNull(message = ValidationErrorTerms.COST_MUST_BE_SET)
    Double cost;
}
