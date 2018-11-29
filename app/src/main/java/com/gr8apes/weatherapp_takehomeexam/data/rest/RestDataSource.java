package com.gr8apes.weatherapp_takehomeexam.data.rest;


import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.domain.usecases.LocationWeatherUseCase;

import io.reactivex.Flowable;

public class RestDataSource implements LocationWeatherUseCase {

    private ApiService mApiService;

    public RestDataSource(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Flowable<CurrentWeatherData> getLocationWeather(String lat, String lon, String appId) {
        return mApiService.getCurrentWeather(lat, lon, appId);
    }

}
