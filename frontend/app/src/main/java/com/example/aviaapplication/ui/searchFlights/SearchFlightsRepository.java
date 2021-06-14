package com.example.aviaapplication.ui.searchFlights;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.api.models.RecentFlight;
import com.example.aviaapplication.api.services.RetrofitConnection;
import com.example.aviaapplication.api.services.flights.FlightApi;
import com.example.aviaapplication.utils.Resource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFlightsRepository {

    private static SearchFlightsRepository searchFlightsRepository;
    private final FlightApi flightApi;

    public static SearchFlightsRepository getInstance() {
        if (searchFlightsRepository == null) {
            searchFlightsRepository = new SearchFlightsRepository();
        }

        return searchFlightsRepository;
    }

    private SearchFlightsRepository() {
        flightApi = RetrofitConnection.createRetrofitConnection(FlightApi.class);
    }

    public void getRecentFlights(String userId, MutableLiveData<Resource<List<RecentFlight>>> liveData) {

        flightApi.getRecentFlights(userId).enqueue(new Callback<List<RecentFlight>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Call<List<RecentFlight>> call, @NotNull Response<List<RecentFlight>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    liveData.postValue(Resource.success(response.body()
                            .stream().filter(flight ->
                                flight.getFlight().getOutboundDate().after(Calendar.getInstance().getTime())
                            ).collect(Collectors.toList())));
                } else {
                    liveData.postValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<RecentFlight>> call, @NotNull Throwable t) {
                liveData.postValue(Resource.error(t.getMessage(), null));
            }
        });
    }

    public Observable<List<Flight>> findFlight(Flight flight) {

        Calendar dateFrom = Calendar.getInstance();
        dateFrom.setTime(flight.getOutboundDate());

        Calendar dateTo = Calendar.getInstance();
        dateTo.setTime(flight.getInboundDate());
        dateTo.add(Calendar.DATE, 1);

        List<Flight> query = new ArrayList<>();

        for (; dateFrom.before(dateTo); dateFrom.add(Calendar.DATE, 1)) {
            query.add(Flight.builder().originPlace(flight.getOriginPlace())
                    .destinationPlace(flight.getDestinationPlace())
                    .outboundDate(dateFrom.getTime()).build());
        }

        return Observable.fromIterable(query).flatMap(flightApi::searchFlights);
    }

    public void addToRecent(String userId, Flight flight) {
        flightApi.addToRecent(new RecentFlight(userId, flight)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                //silent mode
            }
        });
    }

}
