package com.example.aviaApplication.ui.home;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.aviaApplication.R;
import com.example.aviaApplication.api.models.User;

public class HomeViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> currentUser;
    private SharedPreferences preferences;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
        preferences = application.getSharedPreferences(application.getResources().getString(R.string.key_current_user_name), Context.MODE_PRIVATE);

        if (!preferences.getString(getApplication().getResources().getString(R.string.key_current_user_name), "").equals(""))
            currentUser = new MutableLiveData<>(new User(preferences.getString(getApplication().getResources().getString(R.string.key_current_user_name), "")));
        else currentUser = new MutableLiveData<>(null);
    }

    public LiveData<User> getMutableData(){
        return currentUser;
    }

    public void login(){
        currentUser.setValue(userRepository.loginUser());
        preferences.edit().putString(getApplication().getResources().getString(R.string.key_current_user_name),
                currentUser.getValue().getName()).apply();
    }

    public void logout(){
        currentUser.setValue(null);
        preferences.edit().clear().apply();
    }

    public String getUserName(){
        return preferences.getString(getApplication().getResources().getString(R.string.key_current_user_name), "");
    }

    public boolean isAuthorised(){
        return currentUser.getValue() != null;
    }

}