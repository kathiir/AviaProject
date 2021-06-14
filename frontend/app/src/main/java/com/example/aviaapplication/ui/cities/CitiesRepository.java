package com.example.aviaapplication.ui.cities;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.RecentCity;
import com.example.aviaapplication.api.services.RetrofitConnection;
import com.example.aviaapplication.api.services.cities.CitiesApi;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.Resource;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yandex.metrica.impl.ob.Re;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitiesRepository {

    private static CitiesRepository citiesRepository;
    private final CitiesApi citiesApi;


    public static CitiesRepository getInstance() {
        if (citiesRepository == null)
            citiesRepository = new CitiesRepository();

        return citiesRepository;
    }

    private CitiesRepository() {
        citiesApi = RetrofitConnection.createRetrofitConnection(CitiesApi.class);
    }

    public void findByString(String query, MutableLiveData<Resource<List<City>>> liveData) {
        citiesApi.findCity(query).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.body() != null) {
                    List<City> body = response.body()
                            .stream()
                            .filter(CommonUtils.distinctByKey(City::getCityId))
                            .collect(Collectors.toList());
                    body.removeIf(city -> city.getCityId().equals("-sky"));

                    liveData.postValue(Resource.success(body));
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                //TODO
                liveData.postValue(Resource.error(t.getMessage(), null));
                int a = 0;
            }
        });
    }

    public void addCityToRecent(String userId, City city) {
        citiesApi.addToRecentCity(new RecentCity(CommonUtils.cipherEmail(userId), city)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void getRecentCities(String userId, MutableLiveData<Resource<List<City>>> liveData) {
        citiesApi.getRecentCities(CommonUtils.cipherEmail(userId)).enqueue(new Callback<List<RecentCity>>() {
            @Override
            public void onResponse(Call<List<RecentCity>> call, Response<List<RecentCity>> response) {
                if (response.body() != null) {
                    List<City> body = response.body()
                            .stream()
                            .map(recentCity -> recentCity.getCity())
                            .filter(CommonUtils.distinctByKey(City::getCityId))
                            .collect(Collectors.toList());
                    body.removeIf(city -> city.getCityId().equals("-sky"));

                    liveData.postValue(Resource.success(body));
                }
            }

            @Override
            public void onFailure(Call<List<RecentCity>> call, Throwable t) {
                //TODO
                liveData.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }

}
