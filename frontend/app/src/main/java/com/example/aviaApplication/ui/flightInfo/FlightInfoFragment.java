package com.example.aviaApplication.ui.flightInfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aviaApplication.R;
import com.example.aviaApplication.utils.FragmentChangingUtils;
import com.example.aviaApplication.utils.GestureDetectorTurnBack;

public class FlightInfoFragment extends Fragment {
    private ScrollView view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flight_info, container, false);
        view = root.findViewById(R.id.flight_info_ll);
        setUpListeners();
        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpListeners() {
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(FragmentChangingUtils.getOnBackPressedCallback(getParentFragmentManager()));

        GestureDetector mDetector = new GestureDetector(getContext(), new GestureDetectorTurnBack());
        view.setOnTouchListener((View v, MotionEvent event) -> {
            if (mDetector.onTouchEvent(event)) {
                getActivity().onBackPressed();
            }
            return mDetector.onTouchEvent(event);
        });
    }
}
