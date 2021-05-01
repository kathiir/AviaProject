package com.example.aviaApplication.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.example.aviaApplication.api.models.User;

public class UserRepository {
    private static UserRepository userRepository;

    public static UserRepository getInstance(){
        if(userRepository == null)
            userRepository = new UserRepository();
        return userRepository;
    }

    public User loginUser(){
        return new User("Implement repository first");
    }

}
