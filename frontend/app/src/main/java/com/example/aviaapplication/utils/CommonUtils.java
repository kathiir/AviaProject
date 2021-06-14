package com.example.aviaapplication.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.aviaapplication.R;

import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.val;

public class CommonUtils {
    public static OnBackPressedCallback getOnBackPressedCallback(FragmentManager fragmentManager) {
        return new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                fragmentManager.popBackStack();
            }
        };
    }

    public static void goToFragment(FragmentManager fragmentManager, int hostFragmentId, Class<? extends Fragment> target) {
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(hostFragmentId, target, null)
                .addToBackStack(null)
                .commit();
    }

    public static void goToFragment(FragmentManager fragmentManager, int hostFragmentId, Fragment target) {
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(hostFragmentId, target, null)
                .addToBackStack(null)
                .commit();
    }


    public static void makeErrorToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            View view = toast.getView();
            view.getBackground().setColorFilter(context.getColor(R.color.error_red), PorterDuff.Mode.SRC_IN);
            TextView textView = view.findViewById(android.R.id.message);
            textView.setTextColor(context.getColor(R.color.fiord));
        }
        toast.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static String cipherEmail(String email){
        try {
            val digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(email.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexStr = new StringBuilder();
            for (byte aMessageDigest : messageDigest){
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexStr.append(h);
            }
            return hexStr.toString();
        }catch (NoSuchAlgorithmException e){
        }
        return "";

    }

}
