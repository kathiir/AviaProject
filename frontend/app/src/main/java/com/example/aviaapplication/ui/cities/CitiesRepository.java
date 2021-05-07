package com.example.aviaapplication.ui.cities;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.City;

import java.util.List;

public class CitiesRepository {

    private static CitiesRepository citiesRepository;
    private Api api;


    public static CitiesRepository getInstance(){
        if (citiesRepository == null)
            citiesRepository = new CitiesRepository();

        return citiesRepository;
    }

    private CitiesRepository(){
        api = Api.getInstance();
    }

    public List<City> getAllCities(){
        return api.getAllCities();
    }

    public void addCityToRecent(Long userId, City city){
        api.addRecentCity(userId, city);
    }

    public List<City> getRecentCities(Long userId){
        return api.getRecentCities(userId);
    }

}
