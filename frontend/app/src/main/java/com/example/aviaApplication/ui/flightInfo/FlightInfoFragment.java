package com.example.aviaApplication.ui.flightInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aviaApplication.R;
import com.example.aviaApplication.utils.FragmentChangingUtils;

public class FlightInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flight_info, container, false);
        setUpListeners();
        return root;
    }

    private void setUpListeners() {
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(FragmentChangingUtils.getOnBackPressedCallback(getParentFragmentManager()));

    }

}
