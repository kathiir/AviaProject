package com.example.aviaApplication.ui.searchFlights;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aviaApplication.R;
import com.example.aviaApplication.additions.recyclerView.RecentFlightsViewAdapter;
import com.example.aviaApplication.api.models.City;
import com.example.aviaApplication.api.models.Flight;
import com.example.aviaApplication.ui.cities.FragmentCitiesSearch;
import com.example.aviaApplication.ui.foundFlights.FoundFlights;
import com.example.aviaApplication.utils.FragmentChangingUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class SearchFlightsFragment extends Fragment {
    private static TextView countTV, dateTV, cityFromTV, cityToTV;
    private ImageView minusIV, plusIV, swapCities, deleteSecondCity;
    private LinearLayout chooseDateFlights;
    private static Calendar startDate, lastDate;
    public Integer containerId = 0;
    private static City fromCity, toCity;

    private Button searchFlightBtn;
    private static Integer countOfPersons;
    private DialogChooseDateFlight dialogChooseDateFlight;
    private RecentFlightsViewAdapter recentFlightsViewAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_flights, container, false);
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
        if (countOfPersons != null) {
            countTV.setText(Integer.toString(countOfPersons));
        }
    }

    private void initViews(View rootView) {
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
        recentFlightsViewAdapter = new RecentFlightsViewAdapter();
        recyclerView.setAdapter(recentFlightsViewAdapter);
        updateList(new ArrayList<>());
    }


    @SuppressLint("SetTextI18n")
    private void setListeners() {
        minusIV.setOnClickListener(v -> {
            int currState = Integer.parseInt(countTV.getText().toString());
            if (currState > 1) {
                countOfPersons = currState - 1;
                countTV.setText(Integer.toString(currState - 1));
            }
        });
        plusIV.setOnClickListener(v -> {
            int currState = Integer.parseInt(countTV.getText().toString());
            if (currState < 10) {
                countOfPersons = currState + 1;
                countTV.setText(Integer.toString(currState + 1));
            }
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
            Fragment f = new FoundFlights();
            getParentFragmentManager().beginTransaction()
                    .replace(containerId, f)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(SearchFlightsFragment.class.toString())
                    .commit();
        });
        deleteSecondCity.setOnClickListener(v -> {
            setCityTo(null);
        });
        swapCities.setOnClickListener(v -> {
            City fromCityCopy = fromCity;
            setCityFrom(toCity);
            setCityTo(fromCityCopy);
        });

        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(FragmentChangingUtils.getOnBackPressedCallback(getParentFragmentManager()));
    }

    public void updateList(List<Flight> list) {
        list.addAll(Arrays.asList(new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight(), new Flight()));
        recentFlightsViewAdapter.submitList(list);

    }

    public void setCityFrom(City cityFrom) {
        fromCity = cityFrom;
        if (cityFrom != null) {
            cityFromTV.setText(cityFrom.getCityName());
        } else {
            cityFromTV.setText("Откуда");
        }
    }

    public void setCityTo(City cityTo) {
        toCity = cityTo;
        if (cityTo != null) {
            cityToTV.setText(cityTo.getCityName());
        } else {
            cityToTV.setText("Куда");
        }
    }

    @SuppressLint("SetTextI18n")
    public void setNewDate(Calendar startDate, Calendar lastDate) {
        this.startDate = startDate;
        this.lastDate = lastDate;
        SimpleDateFormat format = new SimpleDateFormat("d MMMM ");
        if (startDate != null && lastDate != null && startDate.equals(lastDate)) {
            dateTV.setText(format.format(startDate.getTime()));
        } else {
            dateTV.setText(format.format(startDate.getTime()) + "  \n" + format.format(lastDate.getTime()));
        }
    }
}
