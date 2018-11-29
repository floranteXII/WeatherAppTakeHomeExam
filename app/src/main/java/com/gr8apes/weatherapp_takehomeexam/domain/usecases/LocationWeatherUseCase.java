package com.gr8apes.weatherapp_takehomeexam.domain.usecases;

import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;

import io.reactivex.Flowable;

/**
 * Created by LanarD on 29/11/2018.
 */
public interface LocationWeatherUseCase {
    Flowable<CurrentWeatherData> getLocationWeather(String lat, String lon, String appId);
}
