package service.models.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.utils.ValidationErrorTerms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    /**
     * код аэропорта
     */
    @NotNull(message = ValidationErrorTerms.PLACE_ID_MUST_BE_SET)
    String PlaceId;

    /**
     * город аэропорт
     */
    @NotNull(message = ValidationErrorTerms.PLACE_NAME_MUST_BE_SET)
    String PlaceName;

    /**
     * код города
     */
    @NotNull(message = ValidationErrorTerms.CITY_ID_MUST_BE_SET)
    String CityId;

    /**
     * название страны
     */
    @NotNull(message = ValidationErrorTerms.COUNTRY_NAME_MUST_BE_SET)
    String CountryName;
}
