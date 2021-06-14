package com.example.aviaapplication.ui.home;

import android.content.Context;

import com.example.aviaapplication.api.models.User;
import com.example.aviaapplication.utils.CommonUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRepository {

    private static UserRepository userRepository;

    public static UserRepository getInstance(){
        if (userRepository == null)
            userRepository = new UserRepository();
            return userRepository;
    }

    public User getCurrentUser(Context context){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);

        if (account == null)
            return null;
        return new User(account.getDisplayName(), CommonUtils.cipherEmail(account.getEmail()), account.getPhotoUrl());
    }




}
