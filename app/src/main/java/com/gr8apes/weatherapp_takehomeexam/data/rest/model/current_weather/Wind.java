package com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LanarD on 29/11/2018.
 */
public class Wind implements Serializable {

    @SerializedName("speed")
    @Expose
    float speed;

    public float getSpeed() {
        return speed;
    }
}
