package com.gr8apes.weatherapp_takehomeexam.data.source;

import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.domain.usecases.LocationWeatherUseCase;

import io.reactivex.Flowable;

/**
 * Created by LanarD on 29/11/2018.
 */
public class LocationDataSource implements LocationWeatherUseCase {

    @Override
    public Flowable<CurrentWeatherData> getLocationWeather(String lat, String lon, String appId) {
        return null;
    }
}
