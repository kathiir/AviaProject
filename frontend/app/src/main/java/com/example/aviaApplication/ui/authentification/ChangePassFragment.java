package com.example.aviaApplication.ui.authentification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aviaApplication.R;
import com.example.aviaApplication.ui.flightHistory.FlightHistoryFragment;
import com.example.aviaApplication.ui.home.HomeFragment;
import com.example.aviaApplication.utils.FragmentChangingUtils;

public class ChangePassFragment extends Fragment {

    Button backButton;
    Button changePassButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_pass, container, false);
        initViews(root);
        setUpListeners();
        return root;
    }

    private void initViews(View view){
        backButton = view.findViewById(R.id.button_back);
        changePassButton = view.findViewById(R.id.button_change_pass);
    }

    private void setUpListeners(){
        backButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);

            transaction.replace(R.id.nav_host_fragment, HomeFragment.class, null);
            transaction.commit();
        });

        changePassButton.setOnClickListener(v -> {
            //change pass
            Toast toast = Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_LONG);
            toast.show();
        });

        requireActivity().getOnBackPressedDispatcher()
                .addCallback(FragmentChangingUtils.getOnBackPressedCallback(getParentFragmentManager()));

    }
}
