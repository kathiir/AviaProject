package com.example.aviaapplication.api.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase implements Serializable {
    String userId;
    Flight flight;
    Integer countPassengers;
    Double flightCost;
}
