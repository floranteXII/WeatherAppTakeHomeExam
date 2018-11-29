package com.gr8apes.weatherapp_takehomeexam.presentation.activity;

import android.os.CountDownTimer;
import android.os.Bundle;

import com.gr8apes.weatherapp_takehomeexam.R;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.LocalizationUtils;
import com.gr8apes.weatherapp_takehomeexam.data.preference.Preferences;

import java.util.Locale;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String LANGUAGE = Preferences.getString(mContext, Preferences.LANGUAGE);
        LocalizationUtils.setLocale(new Locale(LANGUAGE));
        LocalizationUtils.setConfigChange(mContext);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                MainActivity.newActivity(mContext);
                finish();
            }
        }.start();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_splash_screen;
    }
}
