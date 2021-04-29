package com.example.aviaApplication.utils.timer;

import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;


public class SplashTimerTask extends TimerTask {
    private Handler mHandler;

    public SplashTimerTask(Handler mHandler) {
        this.mHandler = mHandler;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            Message message = mHandler.obtainMessage();
            message.arg1 = 50;
            message.sendToTarget();
            this.cancel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
