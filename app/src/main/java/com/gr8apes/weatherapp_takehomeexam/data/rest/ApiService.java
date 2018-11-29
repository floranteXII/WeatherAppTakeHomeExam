package com.gr8apes.weatherapp_takehomeexam.data.rest;

import com.google.gson.JsonObject;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "http://api.openweathermap.org/";

    @GET("/data/2.5/weather?")
    Flowable<CurrentWeatherData> getCurrentWeather(@Query("lat") String lat,
                                                   @Query("lon") String lon,
                                                   @Query("appId") String appId);

}
