package com.example.aviaapplication.ui.cities;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.User;
import com.example.aviaapplication.ui.home.LoginViewModel;
import com.example.aviaapplication.ui.home.UserRepository;
import com.example.aviaapplication.utils.Resource;

import java.util.List;

public class CitiesViewModel extends AndroidViewModel {

    private CitiesRepository citiesRepository;
    private UserRepository userRepository;

    private final MutableLiveData<Resource<List<City>>> cityListLiveData;
    private final MutableLiveData<Resource<List<City>>> recentCityLiveData;

    public CitiesViewModel(@NonNull Application application) {
        super(application);
        citiesRepository = CitiesRepository.getInstance();
        userRepository = UserRepository.getInstance();

        cityListLiveData = new MutableLiveData<>();
        recentCityLiveData = new MutableLiveData<>();
        getRecentCities();
    }

    public void findByString(String query){
        citiesRepository.findByString(query, cityListLiveData);
    }

    public LiveData<Resource<List<City>>> getCityListLiveData(){ return cityListLiveData; }

    public LiveData<Resource<List<City>>> getRecentCityLiveData(){ return recentCityLiveData; }

    public void addRecentCity(City city){
        User currentUser = userRepository.getCurrentUser(getApplication().getApplicationContext());
        if (currentUser != null)
            citiesRepository.addCityToRecent(currentUser.getEmail(), city);
    }

    public void getRecentCities(){
        User currentUser = userRepository.getCurrentUser(getApplication().getApplicationContext());
        if (currentUser != null)
            citiesRepository.getRecentCities(currentUser.getEmail(), recentCityLiveData);
    }

    public boolean isLoggedIn(Context context){
        return userRepository.getCurrentUser(context) != null;
    }
}
