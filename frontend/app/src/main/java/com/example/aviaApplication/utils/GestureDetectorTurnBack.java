package com.example.aviaApplication.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureDetectorTurnBack implements GestureDetector.OnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        System.out.println("eeeeeeeeeeeeeeeerrrrrrrrrrrrr");
        return Math.abs(velocityX) > Math.abs(velocityY);
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }
}
