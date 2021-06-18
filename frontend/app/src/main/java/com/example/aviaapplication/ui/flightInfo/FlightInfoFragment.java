package com.example.aviaapplication.ui.flightInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.ui.searchFlights.SearchFlightsFragment;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.GestureDetectorTurnBack;
import com.example.aviaapplication.utils.Resource;
import com.google.android.material.button.MaterialButton;
import com.yandex.metrica.YandexMetrica;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import lombok.val;

public class FlightInfoFragment extends Fragment {
    private ScrollView view;

    private FlightInfoViewModel flightInfoViewModel;

    private static String KEY_FLIGHT_ID = "flightId";

    private Flight flight;
    private Integer persAmount;
    private Double cost;

    private TextView costTV;
    private TextView depTimeTV;
    private TextView depDateTV;
    private TextView depCityTV;
    private TextView depAirportTV;

    private TextView landTimeTV;
    private TextView landDateTV;
    private TextView landCityTV;
    private TextView landAirportTV;

    private TextView flightTimeDurationTV;
    private Button buttonBuy;
    private CheckBox favCheckbox;
    private CheckBox tzCheckbox;

    private MaterialButton buttonBusiness;
    private MaterialButton buttonEconomy;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_flight_info, container, false);
        view = root.findViewById(R.id.flight_info_ll);

        flightInfoViewModel = ViewModelProviders.of(getActivity()).get(FlightInfoViewModel.class);
        flightInfoViewModel.setFlight(flight);


        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.key_current_passenger_count_file), Context.MODE_PRIVATE);
        persAmount = sharedPref.getInt(getString(R.string.key_current_passenger_count), 0);//TODO

        initViews(root);
        setUpListeners();
        return root;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews(View root) {
        buttonBusiness = root.findViewById(R.id.business_ticket);
        buttonEconomy = root.findViewById(R.id.economy_ticket);

        costTV = root.findViewById(R.id.ticket_cost);
        depTimeTV = root.findViewById(R.id.flight_info_departure_time_tv);
        depDateTV = root.findViewById(R.id.flight_info_departure_date_tv);
        depCityTV = root.findViewById(R.id.flight_info_departure_city_tv);
        depAirportTV = root.findViewById(R.id.flight_info_departure_airport_tv);

        landTimeTV = root.findViewById(R.id.flight_info_landing_time_tv);
        landDateTV = root.findViewById(R.id.flight_info_landing_date_tv);
        landCityTV = root.findViewById(R.id.flight_info_landing_city_tv);
        landAirportTV = root.findViewById(R.id.flight_info_landing_airport_tv);

        flightTimeDurationTV = root.findViewById(R.id.flight_time_duration_tv);
        buttonBuy = root.findViewById(R.id.button_buy);
        tzCheckbox = root.findViewById(R.id.consider_timezone_chb);
        favCheckbox = root.findViewById(R.id.set_favorite);

        favCheckbox.setChecked(flightInfoViewModel.isFavorite());

        Flight fl = flightInfoViewModel.getFlight().getValue();
        costTV.setText(String.format("%.2f", persAmount * fl.getCost()));

        DateFormat time = new SimpleDateFormat("HH:mm");
        DateFormat date = new SimpleDateFormat("dd MMMM, E", new Locale("ru"));

        depTimeTV.setText(time.format(fl.getOutboundDate()));
        depDateTV.setText(date.format(fl.getOutboundDate()));
        depCityTV.setText(fl.getOriginPlace().getCountryName());
        depAirportTV.setText(fl.getOriginPlace().getPlaceName());

        landTimeTV.setText(time.format(fl.getInboundDate()));
        landDateTV.setText(date.format(fl.getInboundDate()));
        landCityTV.setText(fl.getOriginPlace().getCountryName());
        landAirportTV.setText(fl.getOriginPlace().getPlaceName());

        buttonBuy.setText("Купить за " + String.format("%.2f", persAmount * fl.getCost()) + "₽");
        cost = persAmount * fl.getCost();

        Date substr = new Date(fl.getOutboundDate().getTime() - fl.getInboundDate().getTime());

        flightTimeDurationTV.setText(time.format(substr));

        flightInfoViewModel.addToRecentViewed();
        flightInfoViewModel.initIsFavoriteLiveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    private void setUpListeners() {
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(CommonUtils.getOnBackPressedCallback(getParentFragmentManager()));

        GestureDetector mDetector = new GestureDetector(getContext(), new GestureDetectorTurnBack());
        view.setOnTouchListener((View v, MotionEvent event) -> {
            if (mDetector.onTouchEvent(event)) {
                getActivity().onBackPressed();
            }

            return mDetector.onTouchEvent(event);
        });

        buttonBusiness.setOnClickListener(v -> {
            Flight fl = flightInfoViewModel.getFlight().getValue();
            Random random = new Random();
            random.setSeed(fl.getCost().longValue());
            Double price = fl.getCost() + fl.getCost() / 10d + random.nextDouble() * 10d % fl.getCost();

            price = Math.round(price * 10d) / 10d;

            costTV.setText(String.format("%.2f", persAmount * price));
            buttonBuy.setText("Купить за " + String.format("%.2f", persAmount * price) + "₽");
            cost = persAmount * price;
        });

        buttonEconomy.setOnClickListener(v -> {
            Flight fl = flightInfoViewModel.getFlight().getValue();
            costTV.setText(String.format("%.2f", persAmount * fl.getCost()));
            buttonBuy.setText("Купить за " + String.format("%.2f", persAmount * fl.getCost()) + "₽");
            cost = persAmount * fl.getCost();
        });

        buttonBuy.setOnClickListener(v -> {
            if (flightInfoViewModel.isLoggedIn(getContext())) {
                flightInfoViewModel.buyTicket(flight, cost, persAmount);

                Map<String, Object> result = new HashMap<>();
                result.put("Ticket cost", cost);
                result.put("Passenger count", persAmount);
                YandexMetrica.reportEvent(getString(R.string.event_user_bought_tickets), result);
            } else
                CommonUtils.makeErrorToast(getContext(), getString(R.string.login_error));
        });

        favCheckbox.setOnClickListener(v ->

        {
            if (favCheckbox.isChecked()) {
                if (flightInfoViewModel.isLoggedIn(getContext()))
                    flightInfoViewModel.makeFavorite();
                else
                    CommonUtils.makeErrorToast(getContext(), getString(R.string.login_error));
            } else
                flightInfoViewModel.unfavorite();

            favCheckbox.setChecked(!favCheckbox.isChecked());
        });

        tzCheckbox.setOnClickListener(v -> {
            DateFormat time = new SimpleDateFormat("HH:mm");
            if (flight != null)
                if (tzCheckbox.isChecked()) {
                    val cal = Calendar.getInstance();
                    cal.setTime(flight.getInboundDate());
                    cal.add(Calendar.HOUR, 3);
                    landTimeTV.setText(time.format(cal.getTime()));

                    cal.setTime(flight.getOutboundDate());
                    cal.add(Calendar.HOUR, 3);
                    depTimeTV.setText(time.format(cal.getTime()));
                } else {
                    landTimeTV.setText(time.format(flight.getInboundDate()));
                    depTimeTV.setText(time.format(flight.getOutboundDate()));
                }
        });

        flightInfoViewModel.getIsFavoriteLiveData().

                observe(getViewLifecycleOwner(), new Observer<Resource<Boolean>>() {
                    @Override
                    public void onChanged(Resource<Boolean> booleanResource) {
                        if (booleanResource.getStatus() == Resource.Status.SUCCESS)
                            favCheckbox.setChecked(booleanResource.getData());
                        else
                            CommonUtils.makeErrorToast(getContext(), getString(R.string.connection_error));
                        //CommonUtils.makeErrorToast(getContext(), booleanResource.getMessage());
                    }
                });

        flightInfoViewModel.getIsSuccessful().

                observe(getViewLifecycleOwner(), voidResource ->

                {
                    if (voidResource != null)
                        if (voidResource.getStatus() == Resource.Status.SUCCESS) {
                            flightInfoViewModel.getIsSuccessful().setValue(null);
                            CommonUtils.goToFragment(getParentFragmentManager(), R.id.nav_host_fragment, SearchFlightsFragment.class);
                        } else
                            CommonUtils.makeErrorToast(getContext(), getString(R.string.connection_error));
                });
    }
}

