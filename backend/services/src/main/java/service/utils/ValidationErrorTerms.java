package service.utils;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorTerms {

    public static final String PLACE_ID_MUST_BE_SET = "PlaceId must be set";
    public static final String PLACE_NAME_MUST_BE_SET = "PlaceName must be set";
    public static final String CITY_ID_MUST_BE_SET = "CityId must be set";
    public static final String COUNTRY_NAME_MUST_BE_SET = "CountryName must be set";
    public static final String USER_ID_MUST_BE_SET = "UserId must be set";
    public static final String OUTBOUND_DATE_MUST_BE_SET = "outboundDate must be set";
    public static final String INBOUND_DATE_MUST_BE_SET = "inboundDate must be set";
    public static final String COST_MUST_BE_SET = "Cost must be set";
    public static final String FLIGHT_COST_MUST_BE_SET = "SumPurchase FlightCost must be set";
    public static final String COUNT_PASSENGER_MUST_BE_SET = "CountPassengers must be set";


    private static final Map<String, String> ERRORS = new HashMap<String, String>() {
        {
            put(PLACE_ID_MUST_BE_SET, "установите PlaceId");
            put(PLACE_NAME_MUST_BE_SET, "установите PlaceName");
            put(CITY_ID_MUST_BE_SET, "установите CityId");
            put(COUNTRY_NAME_MUST_BE_SET, "установите CountryName");
            put(USER_ID_MUST_BE_SET, "установите UserId");
            put(OUTBOUND_DATE_MUST_BE_SET, "установите outboundDate");
            put(INBOUND_DATE_MUST_BE_SET, "установите inboundDate");
            put(COST_MUST_BE_SET, "установите Cost");
            put(FLIGHT_COST_MUST_BE_SET, "установите FlightCost");
            put(COUNT_PASSENGER_MUST_BE_SET, "установите CountPassengers");


        }
    };

    public static String getMessageByCode(String code) {
        return ERRORS.get(code);
    }
}
