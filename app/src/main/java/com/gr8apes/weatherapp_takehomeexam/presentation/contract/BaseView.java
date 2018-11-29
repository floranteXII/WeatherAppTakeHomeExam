package com.gr8apes.weatherapp_takehomeexam.presentation.contract;

import android.support.annotation.NonNull;

/**
 * Created by LanarD on 18/09/2018.
 */

public interface BaseView {
    void showError(@NonNull String errorTag, String message);
}

