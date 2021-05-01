package com.example.aviaapplication.ui.home;

import com.example.aviaapplication.api.models.User;

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
