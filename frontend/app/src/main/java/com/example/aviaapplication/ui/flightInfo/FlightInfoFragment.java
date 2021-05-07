package com.example.aviaapplication.ui.flightInfo;

import android.annotation.SuppressLint;
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

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.models.Flight;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.GestureDetectorTurnBack;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class FlightInfoFragment extends Fragment {
    private ScrollView view;
    private FlightInfoViewModel flightInfoViewModel;

    private Long flightId;
    private static String KEY_FLIGHT_ID = "flightId";

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

    private MaterialButton buttonBusiness;
    private MaterialButton buttonEconomy;


    public static FlightInfoFragment getInstance(Long flId) {
        final FlightInfoFragment frag = new FlightInfoFragment();
        final Bundle args = new Bundle();
        args.putLong(KEY_FLIGHT_ID, flId);
        frag.setArguments(args);
        return frag;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        flightId = getArguments().getLong(KEY_FLIGHT_ID);
        View root = inflater.inflate(R.layout.fragment_flight_info, container, false);
        view = root.findViewById(R.id.flight_info_ll);
        flightInfoViewModel = new FlightInfoViewModel(this.getActivity().getApplication(), flightId);
        initViews(root);
        setUpListeners();
        return root;
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
        favCheckbox = root.findViewById(R.id.set_favorite);

        favCheckbox.setChecked(flightInfoViewModel.isFavorite());

        Flight fl = flightInfoViewModel.getFlight().getValue();
        costTV.setText(fl.getEconomyPrice().toString());

        DateFormat time = new SimpleDateFormat("hh:mm");
        DateFormat date = new SimpleDateFormat("dd MMMM, E", new Locale("ru"));

        depTimeTV.setText(time.format(fl.getDepartureDate()));
        depDateTV.setText(date.format(fl.getDepartureDate()));
        depCityTV.setText(fl.getDepCity().getCityName());
        depAirportTV.setText(fl.getDepCity().getCityCode());

        landTimeTV.setText(time.format(fl.getArrivalDate()));
        landDateTV.setText(date.format(fl.getArrivalDate()));
        landCityTV.setText(fl.getArrivalCity().getCityName());
        landAirportTV.setText(fl.getArrivalCity().getCityCode());

        buttonBuy.setText("Купить за " + fl.getEconomyPrice().toString() + "₽");

        Date substr = new Date(fl.getDepartureDate().getTime() - fl.getArrivalDate().getTime());

        flightTimeDurationTV.setText(time.format(substr));

        if(flightInfoViewModel.isLoggedIn())
            flightInfoViewModel.addToRecentViewed();

    }

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
            costTV.setText(fl.getBusinessPrice().toString());
            buttonBuy.setText("Купить за " + fl.getBusinessPrice().toString() + "₽");
        });

        buttonEconomy.setOnClickListener(v -> {
            Flight fl = flightInfoViewModel.getFlight().getValue();
            costTV.setText(fl.getEconomyPrice().toString());
            buttonBuy.setText("Купить за " + fl.getEconomyPrice().toString() + "₽");
        });

        favCheckbox.setOnClickListener(v -> {
            if (flightInfoViewModel.isLoggedIn()) {
                if (!favCheckbox.isChecked())
                    flightInfoViewModel.unfavorite();
                else
                    flightInfoViewModel.makeFavorite();
            }else {
                favCheckbox.setChecked(false);
                CommonUtils.makeErrorToast(this.getContext(), "Сначала авторизуйтесь в личном кабинете");
            }
        });

    }
}
