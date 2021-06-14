package com.example.aviaapplication.ui.flightHistory;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.api.models.Purchase;
import com.example.aviaapplication.ui.home.UserRepository;
import com.example.aviaapplication.utils.Resource;

import java.util.List;

import lombok.val;

public class FlightHistoryViewModel extends AndroidViewModel {

    private final PurchaseRepository flightHistoryRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<Resource<List<Purchase>>> purchases;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FlightHistoryViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
        flightHistoryRepository = PurchaseRepository.getInstance();
        purchases = new MutableLiveData<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestPurchaseHistList(Context context){
        val user = userRepository.getCurrentUser(context);

        if(user == null)
            return;

        flightHistoryRepository.getPurchase(user.getEmail(), purchases);
    }

    public MutableLiveData<Resource<List<Purchase>>> getPurchases(){
        return purchases;
    }
}
