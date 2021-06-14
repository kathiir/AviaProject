package com.example.aviaapplication.ui.favoriteFlights;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.FavoriteFlight;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.home.UserRepository;
import com.example.aviaapplication.utils.Resource;
import com.example.aviaapplication.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import retrofit2.http.PUT;

public class FavoriteFlightsViewModel extends AndroidViewModel {

    private final FavoriteFlightsRepository favoriteFlightsRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<Resource<List<FavoriteFlight>>> favoriteFlightMutableLiveData;
    private final SingleLiveEvent<Resource<Boolean>> isDeleteSuccessfulLiveData;


    public FavoriteFlightsViewModel(@NonNull Application application) {
        super(application);

        favoriteFlightsRepository = FavoriteFlightsRepository.getInstance();
        userRepository = UserRepository.getInstance();
        favoriteFlightMutableLiveData = new MutableLiveData<>();
        isDeleteSuccessfulLiveData = new SingleLiveEvent<>();
    }

    public LiveData<Resource<List<FavoriteFlight>>> getFavoriteFlightLiveData(){
        return favoriteFlightMutableLiveData;
    }

    public LiveData<Resource<Boolean>> getIsDeletedLiveData(){
        return isDeleteSuccessfulLiveData;
    }

    public void removeFavoriteFromList(int position){
        favoriteFlightsRepository
                .deleteFromFavorite(favoriteFlightMutableLiveData.getValue().getData().get(position), isDeleteSuccessfulLiveData);
    }

    public void requestFavoriteList(Context context){
        val user = userRepository.getCurrentUser(context);
        if (user == null)
            return;

        favoriteFlightsRepository.getFavoriteList(user.getEmail(),
                favoriteFlightMutableLiveData);
    }

    public boolean isLoggedIn(Context context){
        return userRepository.getCurrentUser(context) != null;
    }
}
