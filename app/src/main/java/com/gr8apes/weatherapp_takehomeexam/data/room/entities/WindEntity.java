package com.gr8apes.weatherapp_takehomeexam.data.room.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather.Wind;

import java.io.Serializable;

/**
 * Created by LanarD on 29/11/2018.
 */
public class WindEntity implements Serializable {

    @Expose
    float speed;

    public WindEntity() {
    }

    public WindEntity(Wind wind) {
        this.speed = wind.getSpeed();
    }

    public float getSpeed() {
        return speed;
    }
}
