package tp.project.aviaApplication.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import tp.project.aviaApplication.R;

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

    public static void makeErrorToast(Context context, String text){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.getBackground().setColorFilter(context.getColor(R.color.error_red), PorterDuff.Mode.SRC_IN);
        TextView textView = view.findViewById(android.R.id.message);
        textView.setTextColor(context.getColor(R.color.fiord));
        toast.show();
    }

}
