package com.example.aviaApplication.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.aviaApplication.R;
import com.example.aviaApplication.ui.authentification.ChangeNameFragment;
import com.example.aviaApplication.ui.authentification.ChangePassFragment;
import com.example.aviaApplication.ui.flightHistory.FlightHistoryFragment;
import com.example.aviaApplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaApplication.ui.flightInfo.PassengerListFragment;
import com.example.aviaApplication.utils.FragmentChangingUtils;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button paymentHistory, telegramConfirmDialogButton;
    private View telegramConfirmDialogLayout;
    private Button telegramInitDialogButton, temp, temp1;
    private Handler timerHandler;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);
        setUpListeners();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void initViews(View root) {
        paymentHistory = root.findViewById(R.id.button_payment_history);
        telegramInitDialogButton = root.findViewById(R.id.button_teleg_notifications);
        telegramConfirmDialogButton = root.findViewById(R.id.button_get_teleg_link);
        telegramConfirmDialogLayout = root.findViewById(R.id.teleg_confirmation_layout);
        timerHandler = Handler.createAsync(Looper.getMainLooper());
        temp = root.findViewById(R.id.temp);
        temp1 = root.findViewById(R.id.temp1);
    }

    private void setUpListeners() {
        paymentHistory.setOnClickListener(v -> {
            FragmentChangingUtils.goToFragment(getParentFragmentManager(),
                    R.id.nav_host_fragment, FlightHistoryFragment.class);
        });

        telegramInitDialogButton.setOnClickListener(v -> {
            telegramInitDialogButton.setVisibility(View.GONE);
            telegramConfirmDialogLayout.setVisibility(View.VISIBLE);
            timerHandler.postDelayed(timerRunnable, 10000);
        });

        telegramConfirmDialogButton.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this.getContext(), R.string.not_implemented, Toast.LENGTH_LONG);
            toast.show();
        });


        temp.setOnClickListener(v -> {
            FragmentChangingUtils.goToFragment(getParentFragmentManager(),
                    R.id.nav_host_fragment, FlightInfoFragment.class);
        });

        temp1.setOnClickListener(v -> {
            FragmentChangingUtils.goToFragment(getParentFragmentManager(),
                    R.id.nav_host_fragment, PassengerListFragment.class);
        });

    }


    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            telegramConfirmDialogLayout.setVisibility(View.GONE);
            telegramInitDialogButton.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        telegramConfirmDialogLayout.setVisibility(View.GONE);
        telegramInitDialogButton.setVisibility(View.VISIBLE);
    }
}