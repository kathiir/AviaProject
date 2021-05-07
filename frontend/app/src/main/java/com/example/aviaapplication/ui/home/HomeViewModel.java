package com.example.aviaapplication.ui.home;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aviaapplication.R;
import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class HomeViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> currentUser;
    private SharedPreferences preferences;
    private GoogleSignInAccount account;
    private Api api;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance();
        preferences = application.getSharedPreferences(application.getResources().getString(R.string.key_current_user_name), Context.MODE_PRIVATE);

        api = Api.getInstance();

        if (!preferences.getString(getApplication().getResources().getString(R.string.key_current_user_name), "").equals(""))
            currentUser = new MutableLiveData<>(new User(preferences.getString(getApplication().getResources().getString(R.string.key_current_user_name), "")));
        else currentUser = new MutableLiveData<>(null);
    }

    public LiveData<User> getMutableData(){
        return currentUser;
    }

    public void login(GoogleSignInAccount account){
        this.account = account;

        api.login();

        currentUser.setValue(userRepository.loginUser(account.getDisplayName()));
        preferences.edit().putString(getApplication().getResources().getString(R.string.key_current_user_name),
                currentUser.getValue().getName()).apply();
    }

    public void logout(){
        api.logout();
        currentUser.setValue(null);
        preferences.edit().clear().apply();
    }

    public String getUserName(){
        if (account != null)
            return account.getDisplayName();
        return "";
    }

    public boolean isAuthorised(){
        return currentUser.getValue() != null;
    }

}