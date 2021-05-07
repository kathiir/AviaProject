package com.example.aviaapplication.ui.cities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.City;

import java.util.List;

public class CitiesViewModel extends AndroidViewModel {

    private CitiesRepository citiesRepository;

    public CitiesViewModel(@NonNull Application application) {
        super(application);
        citiesRepository = CitiesRepository.getInstance();
    }

    public List<City> getAllCities(){
        return citiesRepository.getAllCities();
    }

    public void addRecentCity(City city){
        citiesRepository.addCityToRecent(1L, city);
    }

    public List<City> getRecentCities(){
        return citiesRepository.getRecentCities(1L);
    }
}
