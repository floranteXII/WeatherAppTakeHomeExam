package com.gr8apes.weatherapp_takehomeexam.data.room.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Weather;

import java.io.Serializable;

/**
 * Created by LanarD on 28/11/2018.
 */
public class WeatherEntity implements Serializable {

    @Expose
    int id;

    @Expose
    String main;

    @Expose
    String description;

    @Expose
    String icon;

    public WeatherEntity() {
    }

    public WeatherEntity(Weather weather) {
        this.id = weather.getId();
        this.main = weather.getMain();
        this.description = weather.getDescription();
        this.icon = weather.getIcon();
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
