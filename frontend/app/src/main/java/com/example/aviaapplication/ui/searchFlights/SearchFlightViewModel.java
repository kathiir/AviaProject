package com.example.aviaapplication.ui.searchFlights;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.api.models.RecentFlight;
import com.example.aviaapplication.ui.home.UserRepository;
import com.example.aviaapplication.utils.Resource;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import lombok.val;

public class SearchFlightViewModel extends ViewModel {

    private final SearchFlightsRepository flightRepository = SearchFlightsRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();

    private final MutableLiveData<City> departureCity = new MutableLiveData<>();
    private final MutableLiveData<City> arrivalCity = new MutableLiveData<>();
    private final MutableLiveData<Date> inboundDate = new MutableLiveData<>();
    private final MutableLiveData<Date> outboundDate = new MutableLiveData<>();

    private final MutableLiveData<Resource<List<RecentFlight>>> recentFlightListData = new MutableLiveData<>();
    private final MutableLiveData<List<Flight>> flightListData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<City> getDepartureCity(){
        return departureCity;
    }

    public MutableLiveData<City> getArrivalCity(){
        return arrivalCity;
    }

    public MutableLiveData<Date> getInboundDate(){
        return inboundDate;
    }

    public MutableLiveData<Date> getOutboundDate(){
        return outboundDate;
    }

    public LiveData<Resource<List<RecentFlight>>> getRecentFlightsLiveData() {
        return recentFlightListData;
    }

    public MutableLiveData<List<Flight>> getFlightsLiveData(){
        return flightListData;
    }

    public Observable<List<Flight>> findFlights(){
        if (departureCity.getValue() == null || arrivalCity.getValue() == null)
            return null;

        val flight = Flight.builder()
                .originPlace(departureCity.getValue())
                .destinationPlace(arrivalCity.getValue())
                .inboundDate(inboundDate.getValue())
                .outboundDate(outboundDate.getValue()).build();

        return flightRepository.findFlight(flight);
    }

    public void getRecentFlight(Context context){
        if (userRepository.getCurrentUser(context) != null)
            flightRepository.getRecentFlights(userRepository.getCurrentUser(context).getEmail(), recentFlightListData);
    }


}
