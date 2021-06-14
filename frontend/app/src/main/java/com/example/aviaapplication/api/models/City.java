package com.example.aviaapplication.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {

    @SerializedName("placeId")
    private String placeId;

    @SerializedName("countryName")
    private String countryName;

    @SerializedName("cityId")
    private String cityId;

    @SerializedName("placeName")
    private String placeName;

}
