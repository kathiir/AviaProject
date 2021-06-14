package com.example.aviaapplication.api.services.favoriteFlights;

import com.example.aviaapplication.api.models.FavoriteFlight;
import com.example.aviaapplication.api.models.Flight;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavoriteFlightsApi {

    @GET("favorite/{userId}")
    Call<List<FavoriteFlight>> getFavoriteList(@Path("userId") String userId);

    @POST("favorite/likedInfo")
    Call<Integer> getInfo(@Body FavoriteFlight favoriteFlight);

    @POST("favorite/delete")
    Call<Boolean> deleteFromFavorite(@Body FavoriteFlight favoriteFlight);

    @POST("favorite")
    Call<Void> addToFavorite(@Body FavoriteFlight flight);

    @DELETE("favorite/{flight_id}")
    Call<Boolean> deleteFromFavorite(@Path("flight_id") Integer flight_id);
}
