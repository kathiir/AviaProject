package com.example.aviaApplication.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.aviaApplication.R;
import com.example.aviaApplication.ui.flightHistory.FlightHistoryFragment;
import com.example.aviaApplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaApplication.ui.flightInfo.PassengerListFragment;
import com.example.aviaApplication.utils.CommonUtils;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button loginButton, logoutButton, paymentHistory, telegramConfirmDialogButton;
    private View telegramConfirmDialogLayout;
    private TextView usernameTextView;
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
        usernameTextView = root.findViewById(R.id.textview_user_name);
        timerHandler = Handler.createAsync(Looper.getMainLooper());
        loginButton = root.findViewById(R.id.button_login);
        logoutButton = root.findViewById(R.id.button_logout);
        temp = root.findViewById(R.id.temp);  //remove before production
        temp1 = root.findViewById(R.id.temp1);

        if (homeViewModel.isAuthorised()) {
            usernameTextView.setText(homeViewModel.getUserName());
            logoutButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        }

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_main_search);
            }
        });
    }

    private void setUpListeners() {
        homeViewModel.getMutableData().observe(getViewLifecycleOwner(), value -> {
            if (value != null){
                logoutButton.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                usernameTextView.setText(value.getName());
            }else{
                loginButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
                usernameTextView.setText(R.string.title_user_name);
            }
        });

        loginButton.setOnClickListener(v -> {
            homeViewModel.login();
        });

        logoutButton.setOnClickListener(v -> {
            homeViewModel.logout();
        });

        paymentHistory.setOnClickListener(v -> {
            if (homeViewModel.isAuthorised())
                CommonUtils.goToFragment(getParentFragmentManager(),
                        R.id.nav_host_fragment, FlightHistoryFragment.class);
            else
                CommonUtils.makeErrorToast(getContext(), getString(R.string.not_implemented));
//                Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_LONG).show();
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
            CommonUtils.goToFragment(getParentFragmentManager(),
                    R.id.nav_host_fragment, FlightInfoFragment.class);
        });

        temp1.setOnClickListener(v -> {
            CommonUtils.goToFragment(getParentFragmentManager(),
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