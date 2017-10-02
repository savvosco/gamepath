package com.savvo.tosco.gamepath.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import com.savvo.tosco.gamepath.activities.*;
import com.eftimoff.patternview.PatternView;


/**
 * Created by salvotosco on 20/07/17.
 */

public class CountDownTimerWrapperBonus extends CountDownTimer {

    private static final String FORMAT = "%02d:%02d";
    private MainActivity activity;
    private ImageView imageView;

    public CountDownTimerWrapperBonus(long millisInFut, long countDownInterv, PatternView pattern, MainActivity activity,ImageView iv ) {
        super(millisInFut, countDownInterv);
        this.activity=activity;
        this.imageView=iv;
    }


    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        activity.setBonusActve(false);
        imageView.setVisibility(View.INVISIBLE);
    }
}
