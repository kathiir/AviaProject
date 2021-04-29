package com.example.aviaApplication.api.models;

public class City {
    private String cityName;
    private Integer id;

    public City() {
        this.cityName = "Абакан";
        this.id = 1;
    }
    public City(String string) {
        this.cityName = "ZUZAAA";
        this.id = 1;
    }
    public Integer getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }
}
