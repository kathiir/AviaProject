package com.example.aviaapplication.api.models;

public class City {
    private Long id;
    private String cityName;
    private String cityCode;


    public City() {
        this.cityName = "Абакан";
        this.id = Long.valueOf(1);
    }
    public City(String string) {
        this.cityName = string;
        this.id = Long.valueOf(1);
    }

    public City(Long id, String cityName, String cityCode) {
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }
}
