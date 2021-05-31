package service.models.flightApiModel;

import lombok.Data;

import java.util.List;

@Data
public class AnswerModelFlight {
    List<Quote> Quotes;
    List<Place> Places;
}
