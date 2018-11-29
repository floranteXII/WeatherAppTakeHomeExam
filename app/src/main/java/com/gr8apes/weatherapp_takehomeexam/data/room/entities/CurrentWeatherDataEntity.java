package com.gr8apes.weatherapp_takehomeexam.data.room.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Coordinate;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.CurrentWeatherData;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Main;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Sys;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Weather;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Wind;
import com.gr8apes.weatherapp_takehomeexam.data.room.converter.CoordinateEntityConverter;
import com.gr8apes.weatherapp_takehomeexam.data.room.converter.MainEntityConverter;
import com.gr8apes.weatherapp_takehomeexam.data.room.converter.SysEntityConverter;
import com.gr8apes.weatherapp_takehomeexam.data.room.converter.WeatherEntityConverter;
import com.gr8apes.weatherapp_takehomeexam.data.room.converter.WindEntityConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LanarD on 29/11/2018.
 */

@Entity
public class CurrentWeatherDataEntity {

    @PrimaryKey
    public int id;

    public String name;

    @TypeConverters(WeatherEntityConverter.class)
    public List<WeatherEntity> weatherArrayList;

    @TypeConverters(MainEntityConverter.class)
    public MainEntity mainEntity;

    @TypeConverters(SysEntityConverter.class)
    public SysEntity sys;

    @TypeConverters(WindEntityConverter.class)
    public WindEntity wind;

    @TypeConverters(CoordinateEntityConverter.class)
    public CoordinateEntity coordinate;

    public CurrentWeatherDataEntity() {
    }

    public CurrentWeatherDataEntity(CurrentWeatherData currentWeatherData) {
        this.id = currentWeatherData.getId();
        this.name = currentWeatherData.getName();
        for (Weather weather : currentWeatherData.getWeatherArrayList()) {
            this.weatherArrayList = new ArrayList<>();
            WeatherEntity weatherEntity = new WeatherEntity(weather);
            this.weatherArrayList.add(weatherEntity);
        }
        this.mainEntity = new MainEntity(currentWeatherData.getMain());
        this.sys = new SysEntity(currentWeatherData.getSys());
        this.wind = new WindEntity(currentWeatherData.getWind());
        this.coordinate = new CoordinateEntity(currentWeatherData.getCoordinate());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<WeatherEntity> getWeatherArrayList() {
        return weatherArrayList;
    }

    public MainEntity getMain() {
        return mainEntity;
    }

    public SysEntity getSys() {
        return sys;
    }

    public WindEntity getWind() {
        return wind;
    }

    public CoordinateEntity getCoordinate() {
        return coordinate;
    }
}
