package service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.utils.ValidationErrorTerms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    Integer id;
    @NotNull(message = ValidationErrorTerms.USER_ID_MUST_BE_SET)
    String userId;
    @Valid
    Flight flight;
    @NotNull(message = ValidationErrorTerms.COUNT_PASSENGER_MUST_BE_SET)
    Integer countPassengers;
    @NotNull(message = ValidationErrorTerms.FLIGHT_COST_MUST_BE_SET)
    Double flightCost;
}
