package com.example.aviaapplication.utils.timer;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.aviaapplication.MainActivity;
import com.example.aviaapplication.SplashScreenActivity;

public class HandlerSplashScreen extends Handler {
    private SplashScreenActivity splashScreen;

    public HandlerSplashScreen(SplashScreenActivity splashScreen) {
        this.splashScreen = splashScreen;
    }


    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Intent intent = new Intent(splashScreen.getApplicationContext(), MainActivity.class);
        splashScreen.startActivity(intent);
    }
}
