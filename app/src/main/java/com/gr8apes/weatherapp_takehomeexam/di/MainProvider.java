package com.gr8apes.weatherapp_takehomeexam.di;


import com.gr8apes.weatherapp_takehomeexam.presentation.fragment.BaseFragment;
import com.gr8apes.weatherapp_takehomeexam.presentation.fragment.LocationWeatherListFragment;
import com.gr8apes.weatherapp_takehomeexam.presentation.fragment.RefreshButtonContainerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainProvider {

    @ContributesAndroidInjector
    abstract BaseFragment provideBaseFragment();

    @ContributesAndroidInjector
    abstract RefreshButtonContainerFragment providesRefreshButtonContainerFragment();

    @ContributesAndroidInjector
    abstract LocationWeatherListFragment providesLocationWeatherListFragment();

}
