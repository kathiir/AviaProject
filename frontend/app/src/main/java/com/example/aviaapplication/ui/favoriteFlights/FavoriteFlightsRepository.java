package com.example.aviaapplication.ui.favoriteFlights;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.FavoriteFlight;
import com.example.aviaapplication.api.services.RetrofitConnection;
import com.example.aviaapplication.api.services.favoriteFlights.FavoriteFlightsApi;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.Resource;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import lombok.val;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class FavoriteFlightsRepository {

    private static FavoriteFlightsRepository favoriteFlightsRepository;
    private FavoriteFlightsApi favoriteFlightsApi;

    public static FavoriteFlightsRepository getInstance(){
        if (favoriteFlightsRepository == null)
            favoriteFlightsRepository = new FavoriteFlightsRepository();

        return favoriteFlightsRepository;
    }

    private FavoriteFlightsRepository(){
        favoriteFlightsApi = RetrofitConnection.createRetrofitConnection(FavoriteFlightsApi.class);
    }

    public void isFavorite(@NonNull FavoriteFlight favoriteFlight, MutableLiveData<Resource<Boolean>> liveData){
        favoriteFlightsApi.getInfo(favoriteFlight).enqueue(new Callback<Integer>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() != null)
                if (response.body() != 0)
                    liveData.postValue(Resource.success(true));
                else
                    liveData.postValue(Resource.success(false));
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Integer> call, Throwable t) {
                //TODO because responce is null, don't throw error
                liveData.postValue(Resource.error(t.getMessage(),false));
            }
        });
    }

    public void addToFavorite(@NonNull FavoriteFlight flight, MutableLiveData<Resource<Boolean>> liveData){
        favoriteFlightsApi.addToFavorite(flight).enqueue(new Callback<Void>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                    liveData.postValue(Resource.success(true));
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Void> call, Throwable t) {
                liveData.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }

    public void deleteFromFavorite(@NonNull FavoriteFlight favoriteFlight, MutableLiveData<Resource<Boolean>> liveData){

        favoriteFlightsApi.deleteFromFavorite(favoriteFlight).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                liveData.postValue(Resource.success(!response.body()));
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                liveData.postValue(Resource.error(t.getMessage(), null));
                //TODO
            }
        });

    }

    public void getFavoriteList(@NonNull String userId, MutableLiveData<Resource<List<FavoriteFlight>>> liveData){
        favoriteFlightsApi.getFavoriteList(userId).enqueue(new Callback<List<FavoriteFlight>>() {
            @Override
            public void onResponse(@NotNull Call<List<FavoriteFlight>> call, @NotNull Response<List<FavoriteFlight>> response) {
                assert response.body() != null;
                liveData.postValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(@NotNull Call<List<FavoriteFlight>> call, @NotNull Throwable t) {
                liveData.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }

}
