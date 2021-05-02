package com.example.aviaapplication.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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

import com.example.aviaapplication.R;
import com.example.aviaapplication.ui.flightHistory.FlightHistoryFragment;
import com.example.aviaapplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaapplication.ui.flightInfo.PassengerListFragment;
import com.example.aviaapplication.utils.CommonUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment {

    private static final int RC_GET_TOKEN = 9002;
    private static final String TAG = "homeFragment";

    private HomeViewModel homeViewModel;
    private Button loginButton, logoutButton, paymentHistory, telegramConfirmDialogButton;
    private View telegramConfirmDialogLayout;
    private TextView usernameTextView;
    private Button telegramInitDialogButton, temp, temp1;
    private Handler timerHandler;

    private GoogleSignInClient mGoogleSignInClient;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

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


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());


        if (account != null){
            homeViewModel.login(account);
            usernameTextView.setText(account.getDisplayName());
            logoutButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(getString(R.string.server_client_id))
                .requestEmail()
                .requestProfile()
                .requestId()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(getParentFragment().getActivity(), gso);


        //
//        if (homeViewModel.isAuthorised()) {
//            usernameTextView.setText(homeViewModel.getUserName());
//            logoutButton.setVisibility(View.VISIBLE);
//            loginButton.setVisibility(View.GONE);
//        }



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
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_GET_TOKEN);
        });

        logoutButton.setOnClickListener(v -> {
            mGoogleSignInClient.signOut();
            homeViewModel.logout();
        });

        paymentHistory.setOnClickListener(v -> {
            if (homeViewModel.isAuthorised())
                CommonUtils.goToFragment(getParentFragmentManager(),
                        R.id.nav_host_fragment, FlightHistoryFragment.class);
            else
                CommonUtils.makeErrorToast(getContext(), getString(R.string.not_implemented));
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_TOKEN) {
            // [START get_id_token]
            // This task is always completed immediately, there is no need to attach an
            // asynchronous listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            // [END get_id_token]
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // TODO(developer): send ID Token to server and validate
            usernameTextView.setText(account.getDisplayName());
            Toast.makeText(getContext(), "token: " + account.getId(), Toast.LENGTH_LONG).show();
            homeViewModel.login(account);
        } catch (ApiException e) {
            Toast.makeText(getContext(), "NO", Toast.LENGTH_LONG).show();
            usernameTextView.setText(R.string.title_user_name);
            Log.w(TAG, "handleSignInResult:error", e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        telegramConfirmDialogLayout.setVisibility(View.GONE);
        telegramInitDialogButton.setVisibility(View.VISIBLE);
    }
}