package com.example.aviaapplication.ui.home;

import com.example.aviaapplication.api.Api;
import com.example.aviaapplication.api.models.User;

public class UserRepository {
    private static UserRepository userRepository;
    private Api api;


    public static UserRepository getInstance(){
        if(userRepository == null)
            userRepository = new UserRepository(Api.getInstance());
        return userRepository;
    }

    public UserRepository(Api api) {
        this.api = api;
    }

    public User loginUser(String name){
        api.login();
        return new User(name);
    }

    public void logoutUser(){
        api.logout();
    }

    public boolean isLoggedIn(){
        return api.isLoggedIn();
    }
}
