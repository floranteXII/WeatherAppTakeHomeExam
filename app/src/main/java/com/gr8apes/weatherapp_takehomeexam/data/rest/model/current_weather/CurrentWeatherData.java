package com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.CurrentWeatherDataEntity;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.WeatherEntity;
import com.gr8apes.weatherapp_takehomeexam.presentation.utility.ModelUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LanarD on 28/11/2018.
 */
public class CurrentWeatherData implements Serializable {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("weather")
    @Expose
    List<Weather> weatherArrayList;

    @SerializedName("main")
    @Expose
    Main main;

    @SerializedName("sys")
    @Expose
    Sys sys;

    @SerializedName("wind")
    @Expose
    Wind wind;

    @SerializedName("coord")
    @Expose
    Coordinate coordinate;

    public CurrentWeatherData(CurrentWeatherDataEntity currentWeatherDataEntity) {
        this.id = currentWeatherDataEntity.getId();
        this.name = currentWeatherDataEntity.getName();

        String currentWeatherDataString = ModelUtil.toJsonString(currentWeatherDataEntity.getWeatherArrayList());
        Type type = new TypeToken<ArrayList<WeatherEntity>>() {}.getType();
        ArrayList<WeatherEntity> weatherEntities = ModelUtil.fromJson(type, currentWeatherDataString);
        for (WeatherEntity weatherEntity : weatherEntities) {
            this.weatherArrayList = new ArrayList<>();
            Weather weather = new Weather(weatherEntity);
            this.weatherArrayList.add(weather);
        }

        this.main = new Main(currentWeatherDataEntity.getMain());
        this.sys = new Sys(currentWeatherDataEntity.getSys());
        this.wind = new Wind(currentWeatherDataEntity.getWind());
        this.coordinate = new Coordinate(currentWeatherDataEntity.getCoordinate());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Weather> getWeatherArrayList() {
        return weatherArrayList;
    }

    public Main getMain() {
        return main;
    }

    public Sys getSys() {
        return sys;
    }

    public Wind getWind() {
        return wind;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
