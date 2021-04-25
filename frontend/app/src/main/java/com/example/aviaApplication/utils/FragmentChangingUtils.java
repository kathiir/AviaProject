package com.example.aviaApplication.utils;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.aviaApplication.R;
import com.example.aviaApplication.ui.authentification.ChangeNameFragment;

public class FragmentChangingUtils {
    public static OnBackPressedCallback getOnBackPressedCallback(FragmentManager fragmentManager){
        return new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                fragmentManager.popBackStack();
            }
        };
    }

    public static void goToFragment(FragmentManager fragmentManager, int hostFragmentId, Class<? extends Fragment> target){
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(hostFragmentId, target, null)
                .addToBackStack(null)
                .commit();
    }
}
