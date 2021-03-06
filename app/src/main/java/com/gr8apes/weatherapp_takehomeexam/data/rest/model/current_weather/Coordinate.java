package com.gr8apes.weatherapp_takehomeexam.data.rest.model.current_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gr8apes.weatherapp_takehomeexam.data.room.entities.CoordinateEntity;

import java.io.Serializable;

/**
 * Created by LanarD on 29/11/2018.
 */
public class Coordinate implements Serializable {

    @SerializedName("lat")
    @Expose
    double lat;

    @SerializedName("lon")
    @Expose
    double lon;

    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Coordinate(CoordinateEntity coordinateEntity) {
        this.lat = coordinateEntity.getLat();
        this.lon = coordinateEntity.getLon();
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
