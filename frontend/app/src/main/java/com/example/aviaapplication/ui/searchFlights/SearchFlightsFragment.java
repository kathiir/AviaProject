package com.example.aviaapplication.ui.searchFlights;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaapplication.R;
import com.example.aviaapplication.additions.recyclerView.RecentFlightsViewAdapter;
import com.example.aviaapplication.api.models.City;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.cities.FragmentCitiesSearch;
import com.example.aviaapplication.ui.foundFlights.FoundFlights;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.Resource;
import com.yandex.metrica.YandexMetrica;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;


public class SearchFlightsFragment extends Fragment {
    private static TextView countTV, dateTV, cityFromTV, cityToTV;
    private ImageView minusIV, plusIV, swapCities, deleteSecondCity;
    private LinearLayout chooseDateFlights;
    private static Calendar startDate, lastDate;
    public Integer containerId = 0;
    private static City fromCity, toCity;
    private ProgressBar progressBar;

    private Button searchFlightBtn;
    private static Integer countOfPersons;
    private DialogChooseDateFlight dialogChooseDateFlight;
    private RecentFlightsViewAdapter recentFlightsViewAdapter;
    private RecyclerView recyclerView;
    private SearchFlightViewModel searchFlightViewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_flights, container, false);
        assert container != null;
        containerId = container.getId();
        initViews(view);
        setListeners();
        setLastData();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setLastData() {
        if (startDate != null) {
            setNewDate(startDate, lastDate);
        }

        if (fromCity != null) {
            setCityFrom(fromCity);
        }

        if (toCity != null) {
            setCityTo(toCity);
        }

        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.key_current_passenger_count_file), Context.MODE_PRIVATE);
        countOfPersons = sharedPref.getInt(getString(R.string.key_current_passenger_count), 1);

        countTV.setText(Integer.toString(countOfPersons));

    }

    private void initViews(View rootView) {
        searchFlightViewModel = ViewModelProviders.of(requireActivity()).get(SearchFlightViewModel.class);

        progressBar = rootView.findViewById(R.id.search_flights_pb);
        swapCities = rootView.findViewById(R.id.fragment_search_flights_swap_iv);
        deleteSecondCity = rootView.findViewById(R.id.fragment_search_flights_remove_sec_city_iv);
        chooseDateFlights = rootView.findViewById(R.id.choose_data);
        minusIV = rootView.findViewById(R.id.search_flights_count_of_people_minus_iv);
        plusIV = rootView.findViewById(R.id.search_flights_count_of_people_plus_iv);

        countTV = rootView.findViewById(R.id.search_flights_count_of_people_count_tv);
        dateTV = rootView.findViewById(R.id.search_flights_date_tv);
        cityFromTV = rootView.findViewById(R.id.fragment_search_flights_city_from_tv);
        cityToTV = rootView.findViewById(R.id.fragment_search_flights_city_to_tv);

        searchFlightBtn = rootView.findViewById(R.id.search_flights_btn);
        dialogChooseDateFlight = new DialogChooseDateFlight();
        dialogChooseDateFlight.setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        recyclerView = rootView.findViewById(R.id.search_flights_rv);
        recentFlightsViewAdapter = new RecentFlightsViewAdapter(this);
        recyclerView.setAdapter(recentFlightsViewAdapter);

        getRecentFlights();
        searchFlightViewModel.getRecentFlight(getContext());
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private void setListeners() {
        minusIV.setOnClickListener(v -> {
            int currState = Integer.parseInt(countTV.getText().toString());
            if (currState > 1) {
                countOfPersons = currState - 1;
                countTV.setText(Integer.toString(currState - 1));

                Context context = getActivity();
                assert context != null;
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.key_current_passenger_count_file), Context.MODE_PRIVATE);
                sharedPref.edit().putInt(getString(R.string.key_current_passenger_count), countOfPersons).apply();
            }

        });

        plusIV.setOnClickListener(v -> {
            int currState = Integer.parseInt(countTV.getText().toString());
            if (currState < 10) {
                countOfPersons = currState + 1;
                countTV.setText(Integer.toString(currState + 1));
            }

            Context context = getActivity();
            assert context != null;
            SharedPreferences sharedPref = context.getSharedPreferences(
                    getString(R.string.key_current_passenger_count_file), Context.MODE_PRIVATE);
            sharedPref.edit().putInt(getString(R.string.key_current_passenger_count), countOfPersons).apply();
        });

        cityFromTV.setOnClickListener(v -> {
            Fragment f = new FragmentCitiesSearch();
            f.setTargetFragment(this, 1);
            getParentFragmentManager().beginTransaction()
                    .replace(containerId, f, "from")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(SearchFlightsFragment.class.toString())
                    .commit();
        });

        cityToTV.setOnClickListener(v -> {
            Fragment f = new FragmentCitiesSearch();
            f.setTargetFragment(this, 1);
            getParentFragmentManager().beginTransaction()
                    .replace(containerId, f, "to")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(SearchFlightsFragment.class.toString())
                    .commit();
        });

        chooseDateFlights.setOnClickListener(v -> {
            if (!dialogChooseDateFlight.isVisible()) {
                dialogChooseDateFlight.show(getChildFragmentManager(), "");
            }
        });

        searchFlightBtn.setOnClickListener(v -> {
            if (startDate == null || lastDate == null) {
                CommonUtils.makeErrorToast(getContext(), "Укажите дату полета");
                return;
            }

            if (fromCity == null) {
                CommonUtils.makeErrorToast(getContext(), "Укажите пункт отправления");
                return;
            }

            if (toCity == null) {
                CommonUtils.makeErrorToast(getContext(), "Укажите пункт назначения");
                return;
            }

//            Date startDate = SearchFlightsFragment.startDate.getTime();
//            Date lastDate = new Date(SearchFlightsFragment.lastDate.getTime().getTime() + TimeUnit.DAYS.toMillis(1));

            findFlights();


        });

        deleteSecondCity.setOnClickListener(v -> {
            setCityTo(null);
        });

        swapCities.setOnClickListener(v -> {
            City fromCityCopy = fromCity;
            setCityFrom(toCity);
            setCityTo(fromCityCopy);
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_main_search);
            }
        });
    }

    public void setCityFrom(City cityFrom) {
        fromCity = cityFrom;

        if (cityFrom != null) {
            searchFlightViewModel.getDepartureCity().postValue(cityFrom);
            cityFromTV.setText(cityFrom.getPlaceName());

            Map<String, Object> result = new HashMap<>();
            result.put("city_name", cityFrom.getClass());
            YandexMetrica.reportEvent(getString(R.string.event_selected_departure_city), result);
        } else {
            cityFromTV.setText("Откуда");
        }
    }

    public void setCityTo(City cityTo) {
        toCity = cityTo;
        if (cityTo != null) {
            searchFlightViewModel.getArrivalCity().postValue(cityTo);
            cityToTV.setText(cityTo.getPlaceName());

            Map<String, Object> result = new HashMap<>();
            result.put("city_name", cityTo.getPlaceName());
            YandexMetrica.reportEvent(getString(R.string.event_selected_arrival_city), result);
        } else {
            cityToTV.setText("Куда");
        }
    }

    @SuppressLint("SetTextI18n")
    public void setNewDate(Calendar startDate, Calendar lastDate) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("d MMMM ");
        if (startDate != null && lastDate != null) {
            SearchFlightsFragment.startDate = initDate(startDate);
            SearchFlightsFragment.lastDate = initDate(lastDate);

            searchFlightViewModel.getOutboundDate().postValue(startDate.getTime());
            searchFlightViewModel.getInboundDate().postValue(lastDate.getTime());

            Map<String, Object> result = new HashMap<>();
            if (startDate.equals(lastDate)) {
                dateTV.setText(format.format(startDate.getTime()));
                result.put("date", format.format(startDate.getTime()).toString());
            } else {
                dateTV.setText(format.format(startDate.getTime()) + "  \n" + format.format(lastDate.getTime()));
                result.put("date", format.format(startDate.getTime()) + "  \n" + format.format(lastDate.getTime()));
            }

            result.put("date", format.format(startDate.getTime()));
            YandexMetrica.reportEvent(getString(R.string.event_user_selected_dates), result);
        }
    }

    private Calendar initDate(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

    void getRecentFlights() {
        searchFlightViewModel.getRecentFlightsLiveData()
                .observe(getViewLifecycleOwner(), model -> {
                    if (model.getStatus() == Resource.Status.ERROR) {
                        CommonUtils.makeErrorToast(getContext(), getString(R.string.connection_error));
                    } else {
                        recentFlightsViewAdapter.submitList(model.getData());
                    }
                });
    }

    void findFlights() {
        searchFlightViewModel.getIsLoading().setValue(true);

        searchFlightViewModel.getIsLoading().observe(this.getViewLifecycleOwner(), isLoading -> {
            if (isLoading) progressBar.setVisibility(View.VISIBLE);
            else progressBar.setVisibility(View.GONE);
        });

        Map<String, Object> result = new HashMap<>();
        result.put("departure_city", cityFromTV.getText());
        result.put("arrival_city", cityToTV.getText());
        result.put("departure_date", dateTV.getText());
        YandexMetrica.reportEvent(getString(R.string.event_user_perform_search), result);

        Context context = getActivity();
        assert context != null;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.key_current_passenger_count_file), Context.MODE_PRIVATE);
        sharedPref.edit().putInt(getString(R.string.key_current_passenger_count), countOfPersons).apply();

        searchFlightViewModel.getFlightsLiveData().setValue(null);

        List<Flight> flightList = new LinkedList<>();

        searchFlightViewModel.findFlights().observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Flight>>() {
                    @Override
                    public void onNext(@NonNull List<Flight> flights) {
                        if (!flights.isEmpty())
                            flightList.addAll(flights);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                CommonUtils.makeErrorToast(getContext(), e.getLocalizedMessage());
                        CommonUtils.makeErrorToast(getContext(), getString(R.string.connection_error));
                    }

                    @Override
                    public void onComplete() {
                        searchFlightViewModel.getIsLoading().setValue(false);
                        searchFlightViewModel.getFlightsLiveData().postValue(flightList);

                        Fragment f = new FoundFlights();
                        getParentFragmentManager().beginTransaction()
                                .replace(containerId, f)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(SearchFlightsFragment.class.toString())
                                .commit();
                    }
                });


        //        searchFlightViewModel.getFlightsLiveData()
//                .observe(this.getViewLifecycleOwner(), model -> {
//                    if (model == null) {
//                        Toast.makeText(getContext(), "Ошибка получения данных", Toast.LENGTH_SHORT).show();
//                    } else {
//                        searchFlightViewModel.getIsLoading().postValue(false);
//
//                        Fragment f = new FoundFlights();
//                        getParentFragmentManager().beginTransaction()
//                                .replace(containerId, f)
//                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                                .addToBackStack(SearchFlightsFragment.class.toString())
//                                .commit();
//                    }
//                });
    }
}
