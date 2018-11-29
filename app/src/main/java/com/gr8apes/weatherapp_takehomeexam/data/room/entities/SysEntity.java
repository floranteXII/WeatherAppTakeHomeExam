package com.gr8apes.weatherapp_takehomeexam.data.room.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Sys;

import java.io.Serializable;

/**
 * Created by LanarD on 28/11/2018.
 */
public class SysEntity implements Serializable {

    @Expose
    int id;

    @Expose
    String country;

    @Expose
    long sunrise;

    @Expose
    long sunset;

    public SysEntity() {
    }

    public SysEntity(Sys sys) {
        this.id = sys.getId();
        this.country = sys.getCountry();
        this.sunrise = sys.getSunrise();
        this.sunset = sys.getSunset();
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
