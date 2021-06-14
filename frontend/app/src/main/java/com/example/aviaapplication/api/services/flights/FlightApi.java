package com.example.aviaapplication.api.services.flights;

import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.api.models.RecentFlight;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FlightApi {

    @GET("flight/{userId}")
    Call<List<RecentFlight>> getRecentFlights(@Path("userId") String userId);

    @POST("flight/search")
    Observable<List<Flight>> searchFlights(@Body Flight flight);

    @POST("flight")
    Call<ResponseBody> addToRecent(@Body RecentFlight flight);
}
