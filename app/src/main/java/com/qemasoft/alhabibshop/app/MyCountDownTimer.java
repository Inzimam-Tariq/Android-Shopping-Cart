package com.qemasoft.alhabibshop.app;

import android.os.CountDownTimer;
import android.widget.ProgressBar;

/**
 * Created by Inzimam on 24-Dec-17.
 */

public class MyCountDownTimer extends CountDownTimer {
    /**
     * @param millisInFuture    The number of millis in the future from the call
     * to {@link #start()} until the countdown is done and {@link #onFinish()}
     * is called.
     * @param countDownInterval The interval along the way to receive
     * {@link #onTick(long)} callbacks.
     */
    private ProgressBar pb;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, ProgressBar pb) {
        super(millisInFuture, countDownInterval);
        this.pb = pb;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        int progress = (int) (millisUntilFinished / 60);
        pb.setProgress(progress);
    }

    @Override
    public void onFinish() {
        pb.setProgress(0);
    }
}
