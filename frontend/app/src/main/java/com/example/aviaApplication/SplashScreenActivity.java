package com.example.aviaApplication;

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
        setContentView(R.layout.activity_splash_screen);
        messageHandler = new HandlerSplashScreen(this);
        timerTask();
    }

    private void timerTask() {
        SplashTimerTask sty = new SplashTimerTask(messageHandler);
        Timer tn = new Timer();
        tn.schedule(sty, 0, 2400);
    }
}
