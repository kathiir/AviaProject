package com.example.aviaApplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aviaApplication.utils.timer.HandlerSplashScreen;
import com.example.aviaApplication.utils.timer.SplashTimerTask;

import java.util.Timer;

public class SplashScreenActivity extends AppCompatActivity {
    private HandlerSplashScreen messageHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }
}
