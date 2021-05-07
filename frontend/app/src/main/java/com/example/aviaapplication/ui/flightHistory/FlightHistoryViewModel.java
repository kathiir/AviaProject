package com.example.aviaapplication.ui.flightHistory;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.api.models.RecentFlight;
import com.example.aviaapplication.ui.home.UserRepository;

import java.util.List;

public class FlightHistoryViewModel extends AndroidViewModel {

    private FlightHistoryRepository flightHistoryRepository;
    private UserRepository userRepository;
    private MutableLiveData<List<RecentFlight>> flightList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FlightHistoryViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
        flightHistoryRepository = FlightHistoryRepository.getInstance();
        flightList = new MutableLiveData<>(flightHistoryRepository.getFlights());
    }

    public MutableLiveData<List<RecentFlight>> getFlights(){
        return flightList;
    }
}
