package com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

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
    ArrayList<Weather> weatherArrayList;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Weather> getWeatherArrayList() {
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
