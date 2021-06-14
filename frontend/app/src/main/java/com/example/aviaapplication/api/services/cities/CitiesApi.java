package com.example.aviaapplication.api.services.cities;

import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.RecentCity;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CitiesApi {

    @GET("city/{name}")
    Call<List<City>> findCity(@Path("name") String cityName);

    @POST("city/user")
    Call<ResponseBody> addToRecentCity(@Body RecentCity flight);

    @GET("city/user/{user_id}")
    Call<List<RecentCity>> getRecentCities(@Path("user_id") String userId);
}
