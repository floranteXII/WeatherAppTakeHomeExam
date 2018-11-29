package com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.WeatherEntity;

import java.io.Serializable;

/**
 * Created by LanarD on 28/11/2018.
 */
public class Weather implements Serializable {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("main")
    @Expose
    String main;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("icon")
    @Expose
    String icon;

    public Weather(WeatherEntity weatherEntity) {
        this.id = weatherEntity.getId();
        this.main = weatherEntity.getMain();
        this.description = weatherEntity.getDescription();
        this.icon = weatherEntity.getIcon();
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
