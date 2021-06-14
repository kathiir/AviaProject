package com.example.aviaapplication.ui.flightInfo;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.FavoriteFlight;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.api.models.Purchase;
import com.example.aviaapplication.ui.favoriteFlights.FavoriteFlightsRepository;
import com.example.aviaapplication.ui.flightHistory.PurchaseRepository;
import com.example.aviaapplication.ui.home.UserRepository;
import com.example.aviaapplication.ui.searchFlights.SearchFlightsRepository;
import com.example.aviaapplication.utils.Resource;

import lombok.val;

public class FlightInfoViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final FavoriteFlightsRepository favoriteFlightsRepository;
    private final SearchFlightsRepository searchFlightsRepository;
    private final PurchaseRepository purchaseRepository;

    private final MutableLiveData<Flight> flight;
    private final MutableLiveData<Resource<Boolean>> isFalvorite;
    private final MutableLiveData<Resource<Void>> isSuccessful;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FlightInfoViewModel(@NonNull Application application) {
        super(application);

        userRepository = UserRepository.getInstance();
        favoriteFlightsRepository = FavoriteFlightsRepository.getInstance();
        searchFlightsRepository = SearchFlightsRepository.getInstance();
        purchaseRepository = PurchaseRepository.getInstance();

        isFalvorite = new MutableLiveData<>(Resource.success(false));
        flight = new MutableLiveData<>(null);
        isSuccessful = new MutableLiveData<>();
    }

    public void initIsFavoriteLiveData(){
        val user = userRepository.getCurrentUser(getApplication().getApplicationContext());
        if (user == null)
            return;

        val favoriteFlight = FavoriteFlight.builder()
                .flight(flight.getValue())
                .userId(user.getEmail()).build();

        favoriteFlightsRepository.isFavorite(favoriteFlight, isFalvorite);
    }

    public boolean isLoggedIn(Context context){
        return userRepository.getCurrentUser(context) != null;
    }

    public void buyTicket(Flight flight, Double cost, Integer passengerAmount){
        val user = userRepository.getCurrentUser(getApplication().getApplicationContext());
        if (user == null)
            return;

        val purchase = Purchase.builder()
                .flight(flight)
                .flightCost(cost)
                .userId(user.getEmail())
                .countPassengers(passengerAmount)
                .build();

        purchaseRepository.buy(purchase, isSuccessful);
    }


    public void makeFavorite(){
        val user = userRepository.getCurrentUser(getApplication().getApplicationContext());

        if (user == null){
            isFalvorite.postValue(Resource.error("You need to authorise first", false));
            return;
        }

        val favoriteFlight = FavoriteFlight.builder()
                .flight(flight.getValue())
                .userId(user.getEmail()).build();

        favoriteFlightsRepository.addToFavorite(favoriteFlight, isFalvorite);
    }

    public void unfavorite(){
        val user = userRepository.getCurrentUser(getApplication().getApplicationContext());

        if (user == null){
            isFalvorite.postValue(Resource.error("You neet to authorise first", false));
            return;
        }

        val favFlight = FavoriteFlight
                .builder()
                .flight(flight.getValue())
                .userId(user.getEmail())
                .build();

        favoriteFlightsRepository.deleteFromFavorite(favFlight, isFalvorite);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isFavorite(){
        return false;
    }

    public void addToRecentViewed(){
        val user = userRepository.getCurrentUser(getApplication().getApplicationContext());
        if (user == null)
            return;
        searchFlightsRepository.addToRecent(user.getEmail(), flight.getValue());
    }

    public MutableLiveData<Flight> getFlight(){
        return flight;
    }

    public LiveData<Resource<Void>> getIsSuccessful(){
        return isSuccessful;
    }

    public LiveData<Resource<Boolean>> getIsFavoriteLiveData(){
        return isFalvorite;
    }

    public void setFlight(Flight flight){
        this.flight.setValue(flight);
    }
}
