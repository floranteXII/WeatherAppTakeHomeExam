package com.gr8apes.weatherapp_takehomeexam.di;

import com.gr8apes.weatherapp_takehomeexam.presentation.activity.MainActivity;
import com.gr8apes.weatherapp_takehomeexam.presentation.activity.SplashScreenActivity;
import com.gr8apes.weatherapp_takehomeexam.presentation.activity.WeatherDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainProvider.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract WeatherDetailsActivity bindWeatherDetailsActivity();

    @ContributesAndroidInjector
    abstract SplashScreenActivity bindSplashScreenActivity();

}