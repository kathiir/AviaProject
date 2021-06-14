package com.example.aviaapplication.api.services.purchase;

import com.example.aviaapplication.api.models.Purchase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserPurchase {

    @GET("purchase/{userId}")
    Call<List<Purchase>> getListPurchase(@Path("userId") String userId);

    @GET("purchase/count/{userId}")
    Call<Integer> ticketCount(@Path("userId") String userId);

    @POST("purchase/")
    Call<Void> ticketBuy(@Body Purchase purchase);


}
