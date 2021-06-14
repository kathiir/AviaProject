package com.example.aviaapplication.api.models;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight implements Serializable, Cloneable {
    Integer id;
    City originPlace;
    City destinationPlace;
    Date outboundDate;
    Date inboundDate;
    Double cost;
}
