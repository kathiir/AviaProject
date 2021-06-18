package com.example.aviaapplication.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.aviaapplication.R;
import com.example.aviaapplication.ui.flightHistory.FlightHistoryFragment;
import com.example.aviaapplication.ui.flightInfo.FlightInfoFragment;
import com.example.aviaapplication.ui.flightInfo.PassengerListFragment;
import com.example.aviaapplication.utils.ActivityNavigation;
import com.example.aviaapplication.utils.CommonUtils;
import com.example.aviaapplication.utils.Resource;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yandex.metrica.YandexMetrica;

import lombok.val;

public class HomeFragment extends Fragment implements ActivityNavigation {

    private LoginViewModel loginViewModel;
    private Button loginButton, logoutButton, paymentHistory, telegramConfirmDialogButton;
    private View telegramConfirmDialogLayout;
    private TextView usernameTextView;
    private TextView ticketCount;
    private RoundedImageView avatarView;

    private Button telegramInitDialogButton;
    private Handler timerHandler;

    private UserRepository user;

    private final String TELEGRAM_LINK = "https://t.me/avia_project_bot?start=";

    private GoogleSignInClient mGoogleSignInClient;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        this.user = UserRepository.getInstance();

        YandexMetrica.reportEvent(getString(R.string.event_user_swithed_to_home));

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
        avatarView = root.findViewById(R.id.avatar_iv);
        ticketCount = root.findViewById(R.id.tickets_count_textview);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestServerAuthCode(getString(R.string.server_client_id))
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .requestProfile()
                .requestId()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(getParentFragment().getActivity(), gso);

        loginViewModel.setGoogleSignInClient(mGoogleSignInClient);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_main_search);
            }
        });

        loginViewModel.countFlights();
    }

    private void setUpListeners() {
        loginViewModel.startActivityForResultEvent.setEventReceiver(this, this);
        loginViewModel.getMutableData().observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                logoutButton.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                usernameTextView.setText(value.getName());

                Glide.with(this.getContext())
                        .load(value.getImageUri())
                        .into(avatarView);
            } else {
                loginButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
                usernameTextView.setText(R.string.title_user_name);
                avatarView.setImageDrawable(getContext().getDrawable(R.drawable.ic_account));
            }
        });

        loginButton.setOnClickListener(v -> {
            loginViewModel.login();
            YandexMetrica.reportEvent(getString(R.string.event_user_logged_in));
        });

        logoutButton.setOnClickListener(v -> {
            loginViewModel.logout();
            logoutButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            avatarView.setImageDrawable(getContext().getDrawable(R.drawable.ic_account));
            YandexMetrica.reportEvent(getString(R.string.event_user_logged_out));
        });

        paymentHistory.setOnClickListener(v -> {
            if (loginViewModel.isAuthorised())
                CommonUtils.goToFragment(getParentFragmentManager(),
                        R.id.nav_host_fragment, FlightHistoryFragment.class);
            else
                CommonUtils.makeErrorToast(getContext(), "Вам нужно сначала авторизоваться");
        });

        telegramInitDialogButton.setOnClickListener(v -> {
            telegramInitDialogButton.setVisibility(View.GONE);
            telegramConfirmDialogLayout.setVisibility(View.VISIBLE);
            timerHandler.postDelayed(timerRunnable, 10000);
        });

        telegramConfirmDialogButton.setOnClickListener(v -> {

            String url = TELEGRAM_LINK + user.getCurrentUser(getContext()).getEmail();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

            //CommonUtils.makeErrorToast(this.getContext(), getString(R.string.not_implemented));
        });

        loginViewModel.getFlightCountData().observe(getViewLifecycleOwner(),
                integer -> {
                    if (integer.getStatus() == Resource.Status.SUCCESS)
                        ticketCount.setText(integer.getData().toString());
                    else
                        ticketCount.setText(String.valueOf(0));
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
        loginViewModel.onResultFromActivity(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        loginViewModel.countFlights();
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        telegramConfirmDialogLayout.setVisibility(View.GONE);
        telegramInitDialogButton.setVisibility(View.VISIBLE);
    }


}
