package com.example.aviaApplication.ui.searchFlights;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aviaApplication.R;
import com.example.aviaApplication.ui.cities.FragmentCitiesSearch;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SearchFlightsFragment extends Fragment {
    private TextView countTV, dateTV, cityFromTV, cityToTV;
    private ImageView minusIV, plusIV;
    private LinearLayout chh;
    private Calendar startDate, lastDate;
    Integer s = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_flights, container, false);
        s = container.getId();
        initViews(view);
        setListeners();
        return view;
    }

    private void initViews(View rootView) {
        chh = rootView.findViewById(R.id.choose_data);
        minusIV = rootView.findViewById(R.id.search_flights_count_of_people_minus_iv);
        plusIV = rootView.findViewById(R.id.search_flights_count_of_people_plus_iv);
        countTV = rootView.findViewById(R.id.search_flights_count_of_people_count_tv);
        dateTV = rootView.findViewById(R.id.search_flights_date_tv);
        cityFromTV = rootView.findViewById(R.id.fragment_search_flights_city_from_tv);
        cityToTV = rootView.findViewById(R.id.fragment_search_flights_city_to_tv);

    }


    @SuppressLint("SetTextI18n")
    private void setListeners() {
        minusIV.setOnClickListener(v -> {
            int currState = Integer.parseInt(countTV.getText().toString());
            if (currState > 1) {
                countTV.setText(Integer.toString(currState - 1));
            }
        });
        plusIV.setOnClickListener(v -> {
            int currState = Integer.parseInt(countTV.getText().toString());
            if (currState < 10) {
                countTV.setText(Integer.toString(currState + 1));
            }
        });
        cityFromTV.setOnClickListener(v -> {
            Fragment f = new FragmentCitiesSearch();
            getParentFragmentManager().beginTransaction().replace(s, f)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(SearchFlightsFragment.class.toString())
                    .commit();
        });
        cityToTV.setOnClickListener(v -> {
            Fragment f = new FragmentCitiesSearch();
            getParentFragmentManager().beginTransaction().replace(s, f)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(SearchFlightsFragment.class.toString())
                    .commit();
        });
        DialogChooseDateFlight d = new DialogChooseDateFlight();
        d.setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        chh.setOnClickListener(v -> d.show(getChildFragmentManager(), ""));

    }


    @SuppressLint("SetTextI18n")
    public void setNewDate(Calendar startDate, Calendar lastDate) {
        this.startDate = startDate;
        this.lastDate = lastDate;
        SimpleDateFormat format = new SimpleDateFormat("d MMMM ");
        dateTV.setText(format.format(startDate.getTime()) + " - \n" + format.format(lastDate.getTime()));
    }
}
