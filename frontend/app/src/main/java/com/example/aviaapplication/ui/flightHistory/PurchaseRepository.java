package com.example.aviaapplication.ui.flightHistory;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.Purchase;
import com.example.aviaapplication.api.services.RetrofitConnection;
import com.example.aviaapplication.api.services.purchase.UserPurchase;
import com.example.aviaapplication.utils.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseRepository {
    private static PurchaseRepository flightHistoryRepository;
    private UserPurchase userPurchase;

    public static PurchaseRepository getInstance(){
        if (flightHistoryRepository == null)
            flightHistoryRepository = new PurchaseRepository();
        return flightHistoryRepository;
    }

    private PurchaseRepository(){
        this.userPurchase = RetrofitConnection.createRetrofitConnection(UserPurchase.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getPurchase(@NonNull String userId, @NonNull MutableLiveData<Resource<List<Purchase>>> purchases){
        userPurchase.getListPurchase(userId).enqueue(new Callback<List<Purchase>>() {
            @Override
            public void onResponse(Call<List<Purchase>> call, Response<List<Purchase>> response) {
                purchases.postValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(Call<List<Purchase>> call, Throwable t) {
                purchases.postValue(Resource.error(t.getMessage(), null));
                //TODO
            }
        });
    }

    public void buy(@NonNull Purchase purchase, @NonNull MutableLiveData<Resource<Void>> result){
        userPurchase.ticketBuy(purchase).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                result.postValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                result.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }

    public void countTickets(@NonNull String userId, @NonNull MutableLiveData<Resource<Integer>> result){
        userPurchase.ticketCount(userId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                result.postValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                result.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }


}
