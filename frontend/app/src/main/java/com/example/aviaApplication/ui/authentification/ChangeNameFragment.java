package com.example.aviaApplication.ui.authentification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.aviaApplication.R;
import com.example.aviaApplication.ui.home.HomeFragment;
import com.example.aviaApplication.utils.CommonUtils;

public class ChangeNameFragment extends Fragment {
    private Button backButton;
    private Button changeNameButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_name, container, false);
        initViews(root);
        setUpListeners();
        return root;
    }

    private void initViews(View root) {
        backButton = root.findViewById(R.id.button_back);
        changeNameButton = root.findViewById(R.id.button_change_name);
    }

    private void setUpListeners() {
        backButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.nav_host_fragment, HomeFragment.class, null);
            transaction.commit();
        });

        changeNameButton.setOnClickListener(v -> {
            //change pass
            Toast toast = Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_LONG);
            toast.show();
        });

        requireActivity().getOnBackPressedDispatcher()
                .addCallback(CommonUtils.getOnBackPressedCallback(getParentFragmentManager()));
    }
}
