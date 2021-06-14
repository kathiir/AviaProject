package com.example.aviaapplication.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentCity implements Serializable {

    @SerializedName("userId")
    private String email;

    @SerializedName("city")
    private City city;
}
