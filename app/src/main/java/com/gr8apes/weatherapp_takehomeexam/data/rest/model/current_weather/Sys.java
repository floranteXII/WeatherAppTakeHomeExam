package com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.SysEntity;

import java.io.Serializable;

/**
 * Created by LanarD on 28/11/2018.
 */
public class Sys implements Serializable {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("country")
    @Expose
    String country;

    @SerializedName("sunrise")
    @Expose
    long sunrise;

    @SerializedName("sunset")
    @Expose
    long sunset;

    public Sys(SysEntity sysEntity) {
        this.id = sysEntity.getId();
        this.country = sysEntity.getCountry();
        this.sunrise = sysEntity.getSunrise();
        this.sunset = sysEntity.getSunset();
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
