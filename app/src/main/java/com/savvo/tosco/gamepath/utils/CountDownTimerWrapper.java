package com.savvo.tosco.gamepath.utils;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

import com.eftimoff.patternview.PatternView;
import com.savvo.tosco.gamepath.activities.MainActivity;

import java.util.concurrent.TimeUnit;


/**
 * Created by salvotosco on 20/07/17.
 */

public class CountDownTimerWrapper extends CountDownTimer {

    private static final String FORMAT = "%02d:%02d";
    private MainActivity activity;
    private Handler timerHandler;

    public CountDownTimerWrapper(long millisInFut, long countDownInterv, PatternView pattern, MainActivity activity, Handler handler) {
        super(millisInFut, countDownInterv);
        this.activity=activity;
        this.timerHandler=handler;

    }

    @Override
    public void onTick(long millisUntilFinished) {

        this.activity.getTimerTextView().setText(""+String.format(FORMAT,
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

        Message msg = new Message();
        msg.what = (int)millisUntilFinished/1000;
        timerHandler.sendMessage(msg);

    }

    @Override
    public void onFinish() {

        this.activity.getTimerTextView().setText("");
        this.activity.setTimerOver(true);

        if(activity.isTimerOver() && !activity.isPatterneStarted())
            this.activity.startResultsAcitivty();
    }
}
