package com.example.aviaapplication.ui.searchFlights;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.example.aviaapplication.R;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class DialogChooseDateFlight extends DialogFragment {
    private String START_DATE = "start date";
    private String END_DATE = "end date";
    private Calendar startDate;
    private Calendar lastDate;
    private Button doneChooseBtn;
    private CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.dialog_choose_date, container, false);
        initViews(rootView);
        setStyleCalendar();
        setListeners();
        return rootView;
    }

    private void setListeners() {
        doneChooseBtn.setOnClickListener(v -> {
            List<Calendar> calendars = calendarView.getSelectedDates();
            if (calendars.size() > 0) {
                startDate = calendars.get(0);
                lastDate = calendars.get(calendars.size() - 1);
            }
            ((SearchFlightsFragment) getParentFragment()).setNewDate(startDate, lastDate);
            dismiss();
        });
    }

    private void setStyleCalendar() {
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);

        calendarView.setSelectionType(SelectionType.RANGE);
        calendarView.setWeekendDays(new HashSet() {{
            add(Calendar.SATURDAY);
            add(Calendar.SUNDAY);
        }});
        calendarView.setShowDaysOfWeekTitle(false);
        calendarView.setShowDaysOfWeek(true);
        calendarView.setWeekendDayTextColor(Color.parseColor("#4A51F0"));
        calendarView.setSelectedDayBackgroundColor(Color.parseColor("#969AEC"));
        calendarView.setSelectedDayBackgroundStartColor(Color.parseColor("#4A51F0"));
        calendarView.setSelectedDayBackgroundEndColor(Color.parseColor("#4A51F0"));
    }


    private void initViews(View rootView) {
        calendarView = rootView.findViewById(R.id.dialog_choose_date_cosmo_calendar);
        doneChooseBtn = rootView.findViewById(R.id.dialog_choose_date_btn);
    }

}
