package com.gr8apes.weatherapp_takehomeexam.presentation.contract;

import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Coordinate;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;

import java.util.ArrayList;

/**
 * Created by LanarD on 29/11/2018.
 */
public interface LocationWeatherContract {
    interface Presenter {
        void getLocationWeather(String lat, String lon, String appId);

        void getLocationsWeather(ArrayList<Coordinate> coordinates, String appId);
    }

    interface View extends LoadingView {
        void onGetLocationWeatherSuccess(CurrentWeatherData currentWeatherData);

        void onGetLocationsWeatherSuccess(ArrayList<CurrentWeatherData> currentWeatherDatas);
    }
}
